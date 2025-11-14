package com.lab11.views;

import com.lab11.domain.entities.Alumno;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
import java.util.List;
import java.util.Map;

@Component("listar.pdf")
public class AlumnoPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");

        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingAfter(20);

        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase("Lista de Alumnos"));
        cell.setBackgroundColor(new Color(0, 150, 136));
        cell.setPadding(8f);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase("ID"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(4f);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase("Nombre"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(4f);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase("Creditos"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(4f);
        tabla.addCell(cell);

        for (Alumno alumno : alumnos) {
            tabla.addCell(String.valueOf(alumno.getId()));
            tabla.addCell(alumno.getNombre());
            tabla.addCell(String.valueOf(alumno.getCreditos()));
        }

        document.add(tabla);
    }
}
