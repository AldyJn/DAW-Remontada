package com.lab10.views;

import com.lab10.domain.entities.Alumno;
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

@Component("alumno/ver.pdf")
public class AlumnoPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"alumno_view.pdf\"");

        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);

        PdfPCell celda = null;
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 20, Color.BLUE);
        celda = new PdfPCell(new Phrase("Lista de Alumnos", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(40, 190, 138));
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(20);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaAlumnos = new PdfPTable(3);
        tablaAlumnos.setWidths(new float[]{1.5f, 3f, 2f});

        Font fuenteHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, Color.BLUE);
        celda = new PdfPCell(new Phrase("id", fuenteHeader));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnos.addCell(celda);

        celda = new PdfPCell(new Phrase("nombre", fuenteHeader));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnos.addCell(celda);

        celda = new PdfPCell(new Phrase("creditos", fuenteHeader));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnos.addCell(celda);

        for (Alumno alumno : alumnos) {
            celda = new PdfPCell(new Phrase(alumno.getId() + ""));
            celda.setPadding(5);
            tablaAlumnos.addCell(celda);

            celda = new PdfPCell(new Phrase(alumno.getNombre()));
            celda.setPadding(5);
            tablaAlumnos.addCell(celda);

            celda = new PdfPCell(new Phrase(alumno.getCreditos() + ""));
            celda.setPadding(5);
            tablaAlumnos.addCell(celda);
        }

        document.add(tablaTitulo);
        document.add(tablaAlumnos);
    }
}
