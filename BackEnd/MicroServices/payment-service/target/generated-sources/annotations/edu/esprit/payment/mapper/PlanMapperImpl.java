package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.paypal.PlanRequest;
import edu.esprit.payment.entities.Plan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-10T13:45:11+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class PlanMapperImpl implements PlanMapper {

    @Override
    public PlanRequest toPaypalPlanRequest(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanRequest.PlanRequestBuilder planRequest = PlanRequest.builder();

        planRequest.product_id( plan.getProductId() );
        planRequest.name( plan.getName() );
        planRequest.description( plan.getDescription() );

        planRequest.billing_cycles( mapBillingCycles(plan) );
        planRequest.payment_preferences( defaultPaymentPreferences(plan) );
        planRequest.status( "ACTIVE" );

        return planRequest.build();
    }

    @Override
    public Plan toEntity(PlanRequest request) {
        if ( request == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setProductId( request.getProduct_id() );
        plan.setName( request.getName() );
        plan.setDescription( request.getDescription() );

        plan.setAmount( extractAmount(request) );
        plan.setCurrency( extractCurrency(request) );
        plan.setType( extractPlanType(request) );

        return plan;
    }

    @Override
    public Plan toPlan(edu.esprit.payment.dto.PlanRequest request) {
        if ( request == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setName( request.getName() );
        plan.setDescription( request.getDescription() );
        plan.setAmount( request.getAmount() );
        plan.setCurrency( request.getCurrency() );
        plan.setType( request.getType() );

        return plan;
    }
}
