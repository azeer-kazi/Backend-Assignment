package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController

public class Controller {


    @PostMapping("/invoice")
    public ResponseEntity<byte[]> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        // Here, the InvoiceRequest object will contain the JSON data from the request
        // body

        // Generate PDF document
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add content to PDF document
            Font font = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD);
            Paragraph header = new Paragraph("Invoice Details", font);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);


            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            table.setSpacingBefore(40f);
            PdfPCell cell1 = new PdfPCell(new Paragraph("Seller:\n" + invoiceRequest.getSeller() + "\n"
                    + invoiceRequest.getSellerAddress() + "\n" + "GSTIN:" + invoiceRequest.getBuyerGstin(), boldFont));
            cell1.setPadding(30f);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Buyer:\n" + invoiceRequest.getBuyer() + "\n"
                    + invoiceRequest.getBuyerAddress() + "\n" + "GSTIN:" + invoiceRequest.getBuyerGstin(), boldFont));
            cell2.setPadding(30f);
            table.addCell(cell2);

            PdfPTable table2 = new PdfPTable(4);
            table2.setWidthPercentage(100);

            PdfPCell cell11 = new PdfPCell(new Paragraph("Item", boldFont));
            cell11.setPaddingTop(10f);
            cell11.setPaddingBottom(10f);
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell11);

            PdfPCell cell12 = new PdfPCell(new Paragraph("Quantity", boldFont));
            cell12.setPaddingTop(10f);
            cell12.setPaddingBottom(10f);
            cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell12);

            PdfPCell cell13 = new PdfPCell(new Paragraph("Rate", boldFont));
            cell13.setPaddingTop(10f);
            cell13.setPaddingBottom(10f);
            cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell13);

            PdfPCell cell14 = new PdfPCell(new Paragraph("Amount", boldFont));
            cell14.setPaddingTop(10f);
            cell14.setPaddingBottom(10f);
            cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell14);

            PdfPTable table3 = new PdfPTable(4);
            table3.setWidthPercentage(100);

            Item item = invoiceRequest.getItems().get(0);
            PdfPCell cell21 = new PdfPCell(new Paragraph(item.getName(), boldFont));
            cell21.setPaddingTop(10f);
            cell21.setPaddingBottom(10f);
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cell21);

            PdfPCell cell22 = new PdfPCell(new Paragraph(item.getQuantity(), boldFont));
            cell22.setPaddingTop(10f);
            cell22.setPaddingBottom(10f);
            cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cell22);

            PdfPCell cell23 = new PdfPCell(new Paragraph(Double.toString(item.getRate()), boldFont));
            cell23.setPaddingTop(10f);
            cell23.setPaddingBottom(10f);
            cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cell23);

            PdfPCell cell24 = new PdfPCell(new Paragraph(Double.toString(item.getAmount()), boldFont));
            cell24.setPaddingTop(10f);
            cell24.setPaddingBottom(10f);
            cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cell24);

            table.setSpacingAfter(0f);
            table2.setSpacingBefore(0f);
            table3.setSpacingBefore(0f);

            document.add(table);
            document.add(table2);
            document.add(table3);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        // Return response with PDF bytes
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

}
