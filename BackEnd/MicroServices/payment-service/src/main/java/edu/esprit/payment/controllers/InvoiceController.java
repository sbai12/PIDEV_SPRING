package edu.esprit.payment.controllers;

import edu.esprit.payment.entities.Invoice;
import edu.esprit.payment.mapper.InvoiceMapper;
import edu.esprit.payment.services.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoice")
public class InvoiceController {

    public static final String FILENAME_INVOICE = "inline; filename=invoice_";
    public static final String EXTENSION = ".pdf";
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;


    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices(@RequestParam(required = false) String subscriptionId) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(subscriptionId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getInvoiceById(@PathVariable String id) {
        Invoice invoice= invoiceService.getInvoiceById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, FILENAME_INVOICE + id + EXTENSION)
                .contentType(MediaType.APPLICATION_PDF)
                .body(invoice.getPdf());
    }


}
