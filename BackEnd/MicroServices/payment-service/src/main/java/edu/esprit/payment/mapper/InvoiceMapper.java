package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.InvoiceRequest;
import edu.esprit.payment.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceRequest invoiceRequest);
    InvoiceRequest toInvoiceRequest(Invoice invoice);
}
