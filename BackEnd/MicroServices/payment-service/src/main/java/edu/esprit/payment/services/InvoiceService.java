package edu.esprit.payment.services;

import edu.esprit.payment.entities.Invoice;
import edu.esprit.payment.entities.Payment;
import edu.esprit.payment.entities.Plan;
import edu.esprit.payment.entities.Subscription;
import edu.esprit.payment.enums.InvoiceStatus;
import edu.esprit.payment.exeception.BadRequestException;
import edu.esprit.payment.exeception.NotFoundException;
import edu.esprit.payment.mapper.InvoiceMapper;
import edu.esprit.payment.repositories.InvoiceRepository;
import edu.esprit.payment.repositories.PaymentRepository;
import edu.esprit.payment.repositories.PlanRepository;
import edu.esprit.payment.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final InvoicePdfGenerator invoicePdfGenerator;
    private final InvoiceMapper invoiceMapper;
    private final PlanRepository planRepository;

    public void createInvoice(Invoice invoice) {

        try {
            byte[] pdfBytes = invoicePdfGenerator.generateInvoicePdf(invoice);
            invoice.setPdf(pdfBytes);
        } catch (IOException e) {
            throw new BadRequestException("Error generating invoice PDF");
        }
        if (invoice.getId() == null) {
            invoice.setId("invoice_" + UUID.randomUUID());
        }
        invoiceRepository.save(invoice);
    }
    public Invoice createPaymentInvoice(Invoice invoice, String paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()
              -> new BadRequestException("Payment not found"));

        invoice.setTransactionId(payment.getTransactionId());

        try {
            byte[] pdfBytes = invoicePdfGenerator.generateInvoicePdf(invoice);
            invoice.setPdf(pdfBytes);
        } catch (IOException e) {
            throw new BadRequestException("Error generating invoice PDF");
        }
        if (invoice.getId() == null) {
            invoice.setId("invoice_" + UUID.randomUUID());
        }
        payment.setInvoiceId(invoice.getId());
        paymentRepository.save(payment);
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(String invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(()->new NotFoundException("invoice not found"));
    }

    public List<Invoice> getAllInvoices(String subscriptionId) {
        if (subscriptionId != null) {
            return invoiceRepository.findByTransactionId(subscriptionId);
        }
        return invoiceRepository.findAll();
    }

    public Invoice generateInvoiceForSubscription(Invoice invoice, String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(()
                -> new BadRequestException("Subscription not found"));

        invoice.setTransactionId(subscription.getPaypalSubscriptionId());
        invoice.setBillingAddress(generateBillingAddress());
        invoice.setStatus(InvoiceStatus.PAID);
        Plan plan = planRepository.findById(subscription.getPlanId()).orElseThrow(()->new BadRequestException("no plan id found for this subscription : "+ subscriptionId));
        invoice.setAmount(plan.getAmount());
        invoice.setCurrency(plan.getCurrency());
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setDueDate(LocalDateTime.now().plusDays(7));
        try {
            byte[] pdfBytes = invoicePdfGenerator.generateInvoicePdf(invoice);
            invoice.setPdf(pdfBytes);
        } catch (IOException e) {
            throw new BadRequestException("Error generating invoice PDF");
        }
        if (invoice.getId() == null) {
            invoice.setId("invoice_" + UUID.randomUUID());
        }
        subscription.setInvoiceId(invoice.getId());
        subscriptionRepository.save(subscription);
        return invoiceRepository.save(invoice);
    }

    private Invoice.BillingAddress generateBillingAddress() {
        // to be taken from JWT token user info
        return Invoice.BillingAddress.builder().addressLine1("15 rue de liberté").addressLine2("ariana").city("Ariana").postalCode("2020").country("TUNISIA").build();
    }
}
