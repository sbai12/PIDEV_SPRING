package edu.esprit.payment.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import edu.esprit.payment.entities.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Slf4j
public class InvoicePdfGenerator {

    public byte[] generateInvoicePdf(Invoice invoice) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        try {
            Image logo = Image.getInstance(Objects.requireNonNull(getClass().getResource("/logo.jpg")));
            logo.scaleAbsolute(100, 50); // width, height in points
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            log.error("Could not load logo image: " + e.getMessage());
        }

        // Titre
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Facture #" + invoice.getTransactionId(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // espace

        // Détails transaction
        document.add(new Paragraph("Transaction ID: " + invoice.getTransactionId()));
        document.add(new Paragraph("Montant: " + invoice.getAmount() + " " + invoice.getCurrency()));
        document.add(new Paragraph("Date d'émission: " + formatDate(invoice.getIssueDate())));
        document.add(new Paragraph("Date limite: " + formatDate(invoice.getDueDate())));
        document.add(new Paragraph("Statut: " + invoice.getStatus()));

        document.add(new Paragraph(" ")); // espace

        // Adresse de facturation
        Font subTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        document.add(new Paragraph("Adresse de facturation:", subTitleFont));
        document.add(new Paragraph(invoice.getBillingAddress().getAddressLine1()));
        document.add(new Paragraph(invoice.getBillingAddress().getAddressLine2()));
        document.add(new Paragraph(invoice.getBillingAddress().getCity() + ", " + invoice.getBillingAddress().getPostalCode()));
        document.add(new Paragraph(invoice.getBillingAddress().getCountry()));

        document.close();
        return baos.toByteArray();
    }


    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

