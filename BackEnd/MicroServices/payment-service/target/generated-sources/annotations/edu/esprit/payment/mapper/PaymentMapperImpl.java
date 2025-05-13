package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.PaymentRequest;
import edu.esprit.payment.entities.Payment;
import edu.esprit.payment.enums.PaymentMethod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-10T13:45:11+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toPayment(PaymentRequest request) {
        if ( request == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setUserId( request.getUserId() );
        payment.setAmount( request.getAmount() );
        payment.setCurrency( request.getCurrency() );
        if ( request.getMethod() != null ) {
            payment.setMethod( Enum.valueOf( PaymentMethod.class, request.getMethod() ) );
        }
        payment.setDescription( request.getDescription() );
        payment.setPlanId( request.getPlanId() );

        return payment;
    }

    @Override
    public PaymentRequest toPaymentRequest(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.setUserId( payment.getUserId() );
        paymentRequest.setAmount( payment.getAmount() );
        paymentRequest.setCurrency( payment.getCurrency() );
        if ( payment.getMethod() != null ) {
            paymentRequest.setMethod( payment.getMethod().name() );
        }
        paymentRequest.setDescription( payment.getDescription() );
        paymentRequest.setPlanId( payment.getPlanId() );

        return paymentRequest;
    }
}
