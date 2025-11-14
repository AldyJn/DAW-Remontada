package com.lab11.views;

import com.lab11.domain.entities.Alumno;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import java.util.List;
import java.util.Map;

@Component("listar.xls")
public class AlumnoXlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"alumnos.xls\"");

        Sheet sheet = workbook.createSheet("Alumnos");

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Nombre");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Creditos");
        cell.setCellStyle(headerStyle);

        @SuppressWarnings("unchecked")
        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");

        int rowNum = 1;
        for (Alumno alumno : alumnos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(alumno.getId());
            row.createCell(1).setCellValue(alumno.getNombre());
            row.createCell(2).setCellValue(alumno.getCreditos());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
    }
}
