package edu.esprit.payment.services;


import edu.esprit.payment.clients.PaypalFeignClient;
import edu.esprit.payment.dto.paypal.PlanRequest;
import edu.esprit.payment.dto.paypal.PlanResponse;
import edu.esprit.payment.entities.Plan;
import edu.esprit.payment.exeception.BadRequestException;
import edu.esprit.payment.mapper.PlanMapper;
import edu.esprit.payment.repositories.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository repository;
    @Qualifier("planMapper")
    private final PlanMapper mapper;
    private final PaypalFeignClient paypalFeignClient;
    private final PaypalService paypalService;


    public List<Plan> getAll() {
        return repository.findAll();
    }

    public Plan create(edu.esprit.payment.dto.PlanRequest request) {
        // Convert to local entity
        Plan plan = mapper.toPlan(request);
        plan.setId("plan_" + UUID.randomUUID());

        // Build PayPal request
        PlanRequest paypalRequest = PlanRequest.builder()
                .product_id(request.getProduct_id())
                .name(request.getName())
                .description(request.getDescription())
                .billing_cycles(request.getBilling_cycles())
                .payment_preferences(request.getPayment_preferences())
                .build();

        // Create plan on PayPal
        String accessToken = paypalService.getAccessToken(); // Get OAuth token
        PlanResponse paypalPlan = paypalFeignClient.createPlan("Bearer " + accessToken, paypalRequest);

        // Save PayPal plan ID locally
        plan.setPaypalPlanId(paypalPlan.getId());

        return repository.save(plan);
    }

    public Plan update(String id, edu.esprit.payment.dto.PlanRequest request) {
        var plan = repository.findById(id).orElse(null);
        if (plan == null) {
            throw new BadRequestException("Plan not found with id:" + id);
        }

        Plan planToUpdate = mapper.toPlan(request);
        return repository.save(planToUpdate);
    }

    public void delete(String id) {
        var plan = repository.findById(id).orElse(null);
        if (plan == null) {
            throw new BadRequestException("Plan not found with id:" + id);
        }
        repository.delete(plan);
    }

    public Plan getById(String id) {
        return repository.findById(id).orElse(null);
    }
}
