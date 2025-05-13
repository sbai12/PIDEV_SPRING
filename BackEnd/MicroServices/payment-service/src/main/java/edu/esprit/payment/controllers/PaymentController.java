package edu.esprit.payment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.esprit.payment.dto.InvoiceRequest;
import edu.esprit.payment.dto.PaymentRequest;
import edu.esprit.payment.entities.Payment;
import edu.esprit.payment.mapper.InvoiceMapper;
import edu.esprit.payment.services.InvoiceService;
import edu.esprit.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    public static final String FILENAME_INVOICE = "inline; filename=invoice_";
    public static final String EXTENSION = ".pdf";
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody PaymentRequest request) throws JsonProcessingException {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable String id) {
        Payment payment = service.getPaymentById(id);
        return (payment != null) ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/refund")
    public ResponseEntity<Payment> refund(@PathVariable String id) {
        Payment payment = service.refundPayment(id);
        return (payment != null) ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable String id) {
        return  ResponseEntity.ok(service.approvePayment(id));
    }
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> createInvoice(@PathVariable String id, @RequestBody InvoiceRequest invoiceRequest) {
        var invoice = invoiceService.createPaymentInvoice(invoiceMapper.toInvoice(invoiceRequest), id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, FILENAME_INVOICE + id + EXTENSION)
                .contentType(MediaType.APPLICATION_PDF)
                .body(invoice.getPdf());
    }
}
