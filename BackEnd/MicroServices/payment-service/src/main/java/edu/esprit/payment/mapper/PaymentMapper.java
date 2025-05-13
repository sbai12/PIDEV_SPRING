package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.PaymentRequest;
import edu.esprit.payment.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentRequest request);

    PaymentRequest toPaymentRequest(Payment payment);

}
