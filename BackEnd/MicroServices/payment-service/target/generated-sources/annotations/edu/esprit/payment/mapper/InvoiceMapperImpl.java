package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.BillingAddress;
import edu.esprit.payment.dto.InvoiceRequest;
import edu.esprit.payment.entities.Invoice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-10T13:45:11+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public Invoice toInvoice(InvoiceRequest invoiceRequest) {
        if ( invoiceRequest == null ) {
            return null;
        }

        Invoice.InvoiceBuilder invoice = Invoice.builder();

        invoice.amount( invoiceRequest.getAmount() );
        invoice.currency( invoiceRequest.getCurrency() );
        invoice.billingAddress( billingAddressToBillingAddress( invoiceRequest.getBillingAddress() ) );

        return invoice.build();
    }

    @Override
    public InvoiceRequest toInvoiceRequest(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceRequest invoiceRequest = new InvoiceRequest();

        invoiceRequest.setAmount( invoice.getAmount() );
        invoiceRequest.setCurrency( invoice.getCurrency() );
        invoiceRequest.setBillingAddress( billingAddressToBillingAddress1( invoice.getBillingAddress() ) );

        return invoiceRequest;
    }

    protected Invoice.BillingAddress billingAddressToBillingAddress(BillingAddress billingAddress) {
        if ( billingAddress == null ) {
            return null;
        }

        Invoice.BillingAddress.BillingAddressBuilder billingAddress1 = Invoice.BillingAddress.builder();

        billingAddress1.addressLine1( billingAddress.getAddressLine1() );
        billingAddress1.addressLine2( billingAddress.getAddressLine2() );
        billingAddress1.city( billingAddress.getCity() );
        billingAddress1.postalCode( billingAddress.getPostalCode() );
        billingAddress1.country( billingAddress.getCountry() );

        return billingAddress1.build();
    }

    protected BillingAddress billingAddressToBillingAddress1(Invoice.BillingAddress billingAddress) {
        if ( billingAddress == null ) {
            return null;
        }

        BillingAddress billingAddress1 = new BillingAddress();

        billingAddress1.setAddressLine1( billingAddress.getAddressLine1() );
        billingAddress1.setAddressLine2( billingAddress.getAddressLine2() );
        billingAddress1.setCity( billingAddress.getCity() );
        billingAddress1.setPostalCode( billingAddress.getPostalCode() );
        billingAddress1.setCountry( billingAddress.getCountry() );

        return billingAddress1;
    }
}
