package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Asistencia;
import com.escuela.escuela.service.AsistenciaService;
import com.escuela.escuela.service.impl.AsistenciaServiceImpl;
import com.escuela.escuela.util.DBConn;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/AsistenciaController")
public class AsistenciaController extends HttpServlet {
    private AsistenciaService service = new AsistenciaServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion == null || accion.equals("listar")) {
                listar(request, response);
            } else if(accion.equals("registrar")) {
                cargarDatosFormulario(request, response);
            } else {
                listar(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion.equals("guardar")) {
                guardar(request, response);
            } else if(accion.equals("guardarMasivo")) {
                guardarMasivo(request, response);
            } else if(accion.equals("actualizar")) {
                actualizar(request, response);
            } else if(accion.equals("cargarAlumnos")) {
                cargarAlumnosPorCursoYSemana(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("asistencia/registrar.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String idDetalleStr = request.getParameter("idDetalle");
        if(idDetalleStr != null && !idDetalleStr.isEmpty()) {
            int idDetalle = Integer.parseInt(idDetalleStr);
            List<Asistencia> lista = service.listarAsistenciasAlumno(idDetalle);
            request.setAttribute("asistencias", lista);
        }
        request.getRequestDispatcher("asistencia/listar.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Asistencia asistencia = new Asistencia();
        asistencia.setIdSesion(Integer.parseInt(request.getParameter("idSesion")));
        asistencia.setIdDetalle(Integer.parseInt(request.getParameter("idDetalle")));
        asistencia.setEstado(request.getParameter("estado"));

        service.registrarAsistencia(asistencia);
        response.sendRedirect("AsistenciaController?accion=listar&idDetalle=" + asistencia.getIdDetalle());
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Asistencia asistencia = new Asistencia();
        asistencia.setIdAsistencia(Integer.parseInt(request.getParameter("idAsistencia")));
        asistencia.setIdDetalle(Integer.parseInt(request.getParameter("idDetalle")));
        asistencia.setEstado(request.getParameter("estado"));

        service.actualizarAsistencia(asistencia);
        response.sendRedirect("AsistenciaController?accion=listar&idDetalle=" + asistencia.getIdDetalle());
    }

    private void cargarDatosFormulario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Cargar periodos académicos
            ps = conn.prepareStatement("SELECT * FROM periodo_academico WHERE estado = 1 ORDER BY fecha_inicio DESC");
            rs = ps.executeQuery();
            List<Map<String, Object>> periodos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> per = new HashMap<>();
                per.put("id_periodo", rs.getInt("id_periodo"));
                per.put("nombre_periodo", rs.getString("nombre_periodo"));
                per.put("fecha_inicio", rs.getDate("fecha_inicio"));
                per.put("fecha_fin", rs.getDate("fecha_fin"));
                periodos.add(per);
            }
            request.setAttribute("periodos", periodos);
            rs.close();
            ps.close();

            // Cargar cursos activos
            ps = conn.prepareStatement("SELECT * FROM curso WHERE estado = 1 ORDER BY nombre");
            rs = ps.executeQuery();
            List<Map<String, Object>> cursos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> curso = new HashMap<>();
                curso.put("codigo", rs.getString("codigo"));
                curso.put("nombre", rs.getString("nombre"));
                curso.put("creditos", rs.getInt("creditos"));
                cursos.add(curso);
            }
            request.setAttribute("cursos", cursos);

            request.getRequestDispatcher("asistencia/registrar.jsp").forward(request, response);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) DBConn.getInstance().releaseConnection(conn);
        }
    }

    private void cargarAlumnosPorCursoYSemana(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        String idCurso = request.getParameter("idCurso");
        int semana = Integer.parseInt(request.getParameter("semana"));

        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Primero, buscar o crear la sesión de clase para este curso, periodo y semana
            int idSesion = 0;
            ps = conn.prepareStatement("SELECT id_sesion FROM sesion_clase WHERE id_curso = ? AND id_periodo = ? AND semana = ?");
            ps.setString(1, idCurso);
            ps.setInt(2, idPeriodo);
            ps.setInt(3, semana);
            rs = ps.executeQuery();

            if(rs.next()) {
                idSesion = rs.getInt("id_sesion");
            }
            rs.close();
            ps.close();

            // Cargar alumnos matriculados en el curso con su asistencia de la semana
            String sql = "SELECT dm.id_detalle, a.codigo as codigo_alumno, " +
                        "a.nombre || ' ' || a.apellido as nombre_alumno, " +
                        "COALESCE(ast.id_asistencia, 0) as id_asistencia, " +
                        "COALESCE(ast.estado, '') as estado_actual " +
                        "FROM detalle_matricula dm " +
                        "INNER JOIN matricula m ON dm.id_matricula = m.id_matricula " +
                        "INNER JOIN alumno a ON m.id_alumno = a.codigo " +
                        "LEFT JOIN asistencia ast ON dm.id_detalle = ast.id_detalle " +
                        "   AND ast.id_sesion = ? " +
                        "WHERE dm.id_curso = ? AND m.id_periodo = ? " +
                        "AND dm.estado = 1 AND m.estado = 1 AND a.estado = 1 " +
                        "ORDER BY a.apellido, a.nombre";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSesion);
            ps.setString(2, idCurso);
            ps.setInt(3, idPeriodo);
            rs = ps.executeQuery();

            List<Map<String, Object>> alumnos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> alumno = new HashMap<>();
                alumno.put("id_detalle", rs.getInt("id_detalle"));
                alumno.put("codigo_alumno", rs.getString("codigo_alumno"));
                alumno.put("nombre_alumno", rs.getString("nombre_alumno"));
                alumno.put("id_asistencia", rs.getInt("id_asistencia"));
                alumno.put("estado_actual", rs.getString("estado_actual"));
                alumnos.add(alumno);
            }
            request.setAttribute("alumnos", alumnos);
            request.setAttribute("idPeriodoSeleccionado", idPeriodo);
            request.setAttribute("idCursoSeleccionado", idCurso);
            request.setAttribute("semanaSeleccionada", semana);
            request.setAttribute("idSesion", idSesion);

            // Recargar periodos y cursos para mantener los selects
            rs.close();
            ps.close();

            ps = conn.prepareStatement("SELECT * FROM periodo_academico WHERE estado = 1 ORDER BY fecha_inicio DESC");
            rs = ps.executeQuery();
            List<Map<String, Object>> periodos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> per = new HashMap<>();
                per.put("id_periodo", rs.getInt("id_periodo"));
                per.put("nombre_periodo", rs.getString("nombre_periodo"));
                periodos.add(per);
            }
            request.setAttribute("periodos", periodos);
            rs.close();
            ps.close();

            ps = conn.prepareStatement("SELECT * FROM curso WHERE estado = 1 ORDER BY nombre");
            rs = ps.executeQuery();
            List<Map<String, Object>> cursos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> curso = new HashMap<>();
                curso.put("codigo", rs.getString("codigo"));
                curso.put("nombre", rs.getString("nombre"));
                cursos.add(curso);
            }
            request.setAttribute("cursos", cursos);

            request.getRequestDispatcher("asistencia/registrar.jsp").forward(request, response);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) DBConn.getInstance().releaseConnection(conn);
        }
    }

    private void guardarMasivo(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        String idCurso = request.getParameter("idCurso");
        int semana = Integer.parseInt(request.getParameter("semana"));
        String[] idDetalles = request.getParameterValues("idDetalle");
        String[] estados = request.getParameterValues("estado");
        String[] idAsistencias = request.getParameterValues("idAsistencia");

        Connection conn = DBConn.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            // Primero, buscar o crear la sesión de clase
            int idSesion = 0;
            PreparedStatement ps = conn.prepareStatement("SELECT id_sesion FROM sesion_clase WHERE id_curso = ? AND id_periodo = ? AND semana = ?");
            ps.setString(1, idCurso);
            ps.setInt(2, idPeriodo);
            ps.setInt(3, semana);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                idSesion = rs.getInt("id_sesion");
            } else {
                // Crear nueva sesión de clase si no existe
                rs.close();
                ps.close();
                ps = conn.prepareStatement("INSERT INTO sesion_clase (id_curso, id_periodo, semana, fecha) VALUES (?, ?, ?, CURRENT_DATE) RETURNING id_sesion");
                ps.setString(1, idCurso);
                ps.setInt(2, idPeriodo);
                ps.setInt(3, semana);
                rs = ps.executeQuery();
                if(rs.next()) {
                    idSesion = rs.getInt("id_sesion");
                }
            }
            rs.close();
            ps.close();

            // Guardar asistencias
            for(int i = 0; i < idDetalles.length; i++) {
                if(estados[i] != null && !estados[i].trim().isEmpty()) {
                    int idDetalle = Integer.parseInt(idDetalles[i]);
                    String estado = estados[i];
                    int idAsistencia = Integer.parseInt(idAsistencias[i]);

                    if(idAsistencia > 0) {
                        // Actualizar asistencia existente
                        ps = conn.prepareStatement("UPDATE asistencia SET estado = ? WHERE id_asistencia = ?");
                        ps.setString(1, estado);
                        ps.setInt(2, idAsistencia);
                        ps.executeUpdate();
                        ps.close();
                    } else {
                        // Insertar nueva asistencia
                        CallableStatement cs = conn.prepareCall("{CALL sp_registrar_asistencia(?,?,?)}");
                        cs.setInt(1, idSesion);
                        cs.setInt(2, idDetalle);
                        cs.setString(3, estado);
                        cs.execute();
                        cs.close();
                    }
                }
            }

            conn.commit();
            response.sendRedirect("AsistenciaController?accion=listar&mensaje=Asistencias registradas exitosamente");
        } catch(Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            DBConn.getInstance().releaseConnection(conn);
        }
    }
}
