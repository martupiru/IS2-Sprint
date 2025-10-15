package com.sprint.KioscoPatrones.services;

import java.io.OutputStream;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sprint.KioscoPatrones.entities.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PDF {

    @Autowired
    private ProveedorService ps;

    public void generateDocument(OutputStream outputStream) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, outputStream);
        doc.open();

        Paragraph title = new Paragraph("Hola soy el pdf :).\n");
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK));
        doc.add(title);

        // Subtítulo para la tabla
        Paragraph subtitle = new Paragraph("Tabla de datos:\n");
        subtitle.setAlignment(Element.ALIGN_LEFT);
        subtitle.setFont(new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK));
        doc.add(subtitle);

        // Lista de proveedores
        List<Proveedor> proveedores = ps.findAllActivos();


        PdfPTable table = new PdfPTable(7);
        tableHeader(table);

        for (Proveedor prov : proveedores) {
            addRow(table, prov);
        }

        doc.add(table);
        doc.close();
    }

    private static void tableHeader(PdfPTable table) {
        Stream.of("Id", "Nombre", "Apellido", "Teléfono", "Correo Electrónico", "Eliminado", "CUIT")
                .forEach(title -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(title));
                    table.addCell(header);
                });
    }

    private static void addRow(PdfPTable table, Proveedor prov) {
        table.addCell(String.valueOf(prov.getId()));
        table.addCell(prov.getNombre());
        table.addCell(prov.getApellido());
        table.addCell(prov.getTelefono());
        table.addCell(prov.getCorreoElectronico());
        table.addCell(String.valueOf(prov.isEliminado()));
        table.addCell(prov.getCuit());
    }
}


//    private static void addCustomRow(PdfPTable table) throws URISyntaxException, BadElementException, MalformedURLException, IOException {
//        Path path = Paths.get(ClassLoader.getSystemResource("p3.jpg").toURI());
//
//        Image img = Image.getInstance(path.toAbsolutePath().toString());
//        img.scalePercent(10);
//
//        PdfPCell imageCell = new PdfPCell(img);
//        table.addCell(imageCell);
//
//        PdfPCell desc = new PdfPCell(new Phrase("Description"));
//        desc.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(desc);
//
//        PdfPCell remarks = new PdfPCell(new Phrase("Reamrks"));
//        remarks.setVerticalAlignment(Element.ALIGN_CENTER);
//        table.addCell(remarks);
//    }

