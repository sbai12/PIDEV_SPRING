package edu.esprit.payment.controllers;

import edu.esprit.payment.dto.InvoiceRequest;
import edu.esprit.payment.dto.SubscriptionRequest;
import edu.esprit.payment.entities.Invoice;
import edu.esprit.payment.entities.Subscription;
import edu.esprit.payment.mapper.InvoiceMapper;
import edu.esprit.payment.services.InvoiceService;
import edu.esprit.payment.services.SubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    public static final String FILENAME_INVOICE = "inline; filename=invoice_";
    public static final String EXTENSION = ".pdf";
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;


    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> get(@PathVariable String id) {
        return subscriptionService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> generateInvoiceForSubscription(
            @PathVariable String id,
            @RequestBody InvoiceRequest invoiceRequest
    ) {
        Invoice generated = invoiceService.generateInvoiceForSubscription(invoiceMapper.toInvoice(invoiceRequest),id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, FILENAME_INVOICE + id + EXTENSION)
                .contentType(MediaType.APPLICATION_PDF)
                .body(generated.getPdf());
    }
}

