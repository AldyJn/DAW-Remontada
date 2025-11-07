package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Nota;
import com.escuela.escuela.service.NotaService;
import com.escuela.escuela.service.impl.NotaServiceImpl;
import com.escuela.escuela.util.DBConn;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/NotaController")
public class NotaController extends HttpServlet {
    private NotaService service = new NotaServiceImpl();

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
                cargarAlumnosPorCurso(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("nota/registrar.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String idDetalleStr = request.getParameter("idDetalle");
        if(idDetalleStr != null && !idDetalleStr.isEmpty()) {
            int idDetalle = Integer.parseInt(idDetalleStr);
            List<Nota> lista = service.listarNotasAlumno(idDetalle);
            request.setAttribute("notas", lista);
        }
        request.getRequestDispatcher("nota/listar.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Nota nota = new Nota();
        nota.setIdDetalle(Integer.parseInt(request.getParameter("idDetalle")));
        nota.setIdEvaluacion(Integer.parseInt(request.getParameter("idEvaluacion")));
        nota.setNota(Double.parseDouble(request.getParameter("nota")));

        service.registrarNota(nota);
        response.sendRedirect("NotaController?accion=listar&idDetalle=" + nota.getIdDetalle());
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Nota nota = new Nota();
        nota.setIdNota(Integer.parseInt(request.getParameter("idNota")));
        nota.setIdDetalle(Integer.parseInt(request.getParameter("idDetalle")));
        nota.setNota(Double.parseDouble(request.getParameter("nota")));

        service.actualizarNota(nota);
        response.sendRedirect("NotaController?accion=listar&idDetalle=" + nota.getIdDetalle());
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
            rs.close();
            ps.close();

            // Cargar TODAS las evaluaciones (se filtrarán por JavaScript en el cliente)
            ps = conn.prepareStatement("SELECT * FROM evaluacion ORDER BY id_curso, nombre");
            rs = ps.executeQuery();
            List<Map<String, Object>> todasEvaluaciones = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> eval = new HashMap<>();
                eval.put("id_evaluacion", rs.getInt("id_evaluacion"));
                eval.put("id_curso", rs.getString("id_curso"));
                eval.put("nombre", rs.getString("nombre"));
                eval.put("peso", rs.getDouble("peso"));
                todasEvaluaciones.add(eval);
            }
            request.setAttribute("todasEvaluaciones", todasEvaluaciones);

            request.getRequestDispatcher("nota/registrar.jsp").forward(request, response);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) DBConn.getInstance().releaseConnection(conn);
        }
    }

    private void cargarAlumnosPorCurso(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        String idCurso = request.getParameter("idCurso");
        int idEvaluacion = Integer.parseInt(request.getParameter("idEvaluacion"));

        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Cargar alumnos matriculados en el curso
            String sql = "SELECT dm.id_detalle, a.codigo as codigo_alumno, " +
                        "a.nombre || ' ' || a.apellido as nombre_alumno, " +
                        "COALESCE(n.id_nota, 0) as id_nota, " +
                        "COALESCE(n.nota, 0) as nota_actual " +
                        "FROM detalle_matricula dm " +
                        "INNER JOIN matricula m ON dm.id_matricula = m.id_matricula " +
                        "INNER JOIN alumno a ON m.id_alumno = a.codigo " +
                        "LEFT JOIN nota n ON dm.id_detalle = n.id_detalle AND n.id_evaluacion = ? " +
                        "WHERE dm.id_curso = ? AND m.id_periodo = ? " +
                        "AND dm.estado = 1 AND m.estado = 1 AND a.estado = 1 " +
                        "ORDER BY a.apellido, a.nombre";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idEvaluacion);
            ps.setString(2, idCurso);
            ps.setInt(3, idPeriodo);
            rs = ps.executeQuery();

            List<Map<String, Object>> alumnos = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> alumno = new HashMap<>();
                alumno.put("id_detalle", rs.getInt("id_detalle"));
                alumno.put("codigo_alumno", rs.getString("codigo_alumno"));
                alumno.put("nombre_alumno", rs.getString("nombre_alumno"));
                alumno.put("id_nota", rs.getInt("id_nota"));
                alumno.put("nota_actual", rs.getDouble("nota_actual"));
                alumnos.add(alumno);
            }
            request.setAttribute("alumnos", alumnos);
            request.setAttribute("idPeriodoSeleccionado", idPeriodo);
            request.setAttribute("idCursoSeleccionado", idCurso);
            request.setAttribute("idEvaluacionSeleccionada", idEvaluacion);

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
            rs.close();
            ps.close();

            // Cargar TODAS las evaluaciones para el filtrado por JavaScript
            ps = conn.prepareStatement("SELECT * FROM evaluacion ORDER BY id_curso, nombre");
            rs = ps.executeQuery();
            List<Map<String, Object>> todasEvaluaciones = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> eval = new HashMap<>();
                eval.put("id_evaluacion", rs.getInt("id_evaluacion"));
                eval.put("id_curso", rs.getString("id_curso"));
                eval.put("nombre", rs.getString("nombre"));
                eval.put("peso", rs.getDouble("peso"));
                todasEvaluaciones.add(eval);
            }
            request.setAttribute("todasEvaluaciones", todasEvaluaciones);

            // También cargar las evaluaciones del curso actual
            List<Map<String, Object>> evaluaciones = new ArrayList<>();
            for(Map<String, Object> eval : todasEvaluaciones) {
                if(idCurso.equals(eval.get("id_curso"))) {
                    evaluaciones.add(eval);
                }
            }
            request.setAttribute("evaluaciones", evaluaciones);

            request.getRequestDispatcher("nota/registrar.jsp").forward(request, response);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) DBConn.getInstance().releaseConnection(conn);
        }
    }

    private void guardarMasivo(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int idEvaluacion = Integer.parseInt(request.getParameter("idEvaluacion"));
        String[] idDetalles = request.getParameterValues("idDetalle");
        String[] notas = request.getParameterValues("nota");
        String[] idNotas = request.getParameterValues("idNota");

        Connection conn = DBConn.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            for(int i = 0; i < idDetalles.length; i++) {
                if(notas[i] != null && !notas[i].trim().isEmpty()) {
                    int idDetalle = Integer.parseInt(idDetalles[i]);
                    double nota = Double.parseDouble(notas[i]);
                    int idNota = Integer.parseInt(idNotas[i]);

                    if(idNota > 0) {
                        // Actualizar nota existente
                        PreparedStatement ps = conn.prepareStatement("UPDATE nota SET nota = ? WHERE id_nota = ?");
                        ps.setDouble(1, nota);
                        ps.setInt(2, idNota);
                        ps.executeUpdate();
                        ps.close();
                    } else {
                        // Insertar nueva nota
                        CallableStatement cs = conn.prepareCall("{CALL sp_registrar_nota(?,?,?)}");
                        cs.setInt(1, idDetalle);
                        cs.setInt(2, idEvaluacion);
                        cs.setDouble(3, nota);
                        cs.execute();
                        cs.close();
                    }
                }
            }

            conn.commit();
            response.sendRedirect("NotaController?accion=listar&mensaje=Notas registradas exitosamente");
        } catch(Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            DBConn.getInstance().releaseConnection(conn);
        }
    }
}
