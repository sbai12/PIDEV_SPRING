package edu.esprit.payment.services;

import edu.esprit.payment.clients.PaypalFeignClient;
import edu.esprit.payment.clients.TrainingServiceClient;
import edu.esprit.payment.dto.SubscriptionRequest;

import edu.esprit.payment.dto.paypal.*;
import edu.esprit.payment.entities.Plan;
import edu.esprit.payment.entities.Subscription;
import edu.esprit.payment.enums.PaymentStatus;
import edu.esprit.payment.repositories.PlanRepository;
import edu.esprit.payment.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanService planService;
    private final PaypalFeignClient paypalFeignClient;
    private final PlanRepository planRepository;
    private final PaypalService paypalService;
    private final TrainingServiceClient trainingServiceClient;
    private final JavaMailSender mailSender;




    public Subscription create(SubscriptionRequest req) {
        Subscription sub = new Subscription();
        sub.setId(UUID.randomUUID().toString());
        sub.setUserId(req.getUserId());
        sub.setPlanId(req.getPlanId());
        sub.setStatus(PaymentStatus.PENDING.name());

        if (req.getTrialPeriodDays() != null) {
            sub.setTrialEndDate(LocalDateTime.now().plusDays(req.getTrialPeriodDays()));
        }

        Plan plan = planService.getById(req.getPlanId());

        String token = paypalService.getAccessToken();

        var planRequest = PlanRequest.builder()
                .product_id(plan.getProductId())
                .name(plan.getName())
                .description(plan.getDescription())
                .billing_cycles(List.of(
                        BillingCycle.builder()
                                .frequency(Frequency.builder()
                                        .interval_unit("MONTH")
                                        .interval_count(1)
                                        .build())
                                .tenure_type("REGULAR")
                                .sequence(1)
                                .total_cycles(0)
                                .pricing_scheme(PricingScheme.builder()
                                        .fixed_price(Money.builder()
                                                .currency_code(plan.getCurrency())
                                                .value(String.valueOf(plan.getAmount()))
                                                .build())
                                        .build())
                                .build()
                ))
                .payment_preferences(PaymentPreferences.builder()
                        .auto_bill_outstanding(true)
                        .setup_fee(Money.builder()
                                .currency_code(plan.getCurrency())
                                .value("0")
                                .build())
                        .setup_fee_failure_action("CONTINUE")
                        .payment_failure_threshold(3)
                        .build())
                .build();

        PlanResponse planResponse = paypalFeignClient.createPlan("Bearer " + token, planRequest);
        plan.setPaypalPlanId(planResponse.getId());
        planRepository.save(plan);

        // Step 2: Create Subscription
        var subscriptionRequest = PSubscriptionRequest.builder()
                .plan_id(planResponse.getId())
                .application_context(ApplicationContext.builder()
                        .brand_name("Esprit Education")
                        .locale("en-US")
                        .shipping_preference("NO_SHIPPING")
                        .user_action("SUBSCRIBE_NOW")
                        .return_url("https://yourdomain.com/success")
                        .cancel_url("https://yourdomain.com/cancel")
                        .build())
                .subscriber(Subscriber.builder()
                        .name(Name.builder().given_name("nouha").surname("sayadi").build())
                        .email_address("customer@example.com")
                        .build())
                .build();

        Map<String, Object> subscriptionResponse = paypalFeignClient.createSubscription("Bearer " + token, subscriptionRequest);

        String paypalSubscriptionId = (String) subscriptionResponse.get("id");
        String approvalUrl = ((List<Map<String, String>>) subscriptionResponse.get("links")).stream()
                .filter(link -> "approve".equals(link.get("rel")))
                .map(link -> link.get("href"))
                .findFirst()
                .orElse(null);

        sub.setPaypalSubscriptionId(paypalSubscriptionId);
        sub.setApprovalUrl(approvalUrl);

        return subscriptionRepository.save(sub);
    }
    public void sendNotification(String userId, String productId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", userId);
        payload.put("product_id", productId);
        payload.put("event", "subscription.payment.failed");

        try {
            ResponseEntity<String> response = trainingServiceClient.notifyPaymentFailed(payload);
            log.info("Training service notified : {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("error sending notification : {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    //@Scheduled(cron = "0 * * * * *")
    public void checkUnpaidSubscriptions() {
        log.info(" Checking subscriptions for upcoming trial ends and unpaid status...");

        List<Subscription> activeSubscriptions = getByStatus(PaymentStatus.PENDING.name());
        LocalDate today = LocalDate.now();

        for (Subscription subscription : activeSubscriptions) {
            LocalDate trialEndDate = subscription.getTrialEndDate().toLocalDate();

            if (trialEndDate.minusDays(3).isEqual(today)) {
                log.info(" Trial ending in 3 days for subscription: {} (User: {})",
                        subscription.getId(), subscription.getUserId());
                sendTrialEndingReminderEmail(subscription);
            }

            if (trialEndDate.isBefore(today) && isSubscriptionUnpaid(subscription)) {
                log.warn(" Trial period ended for unpaid subscription: {} (User: {})",
                        subscription.getId(), subscription.getUserId());

                try {
                    sendNotification(subscription.getUserId(), subscription.getPlanId());
                    log.info(" Block notification sent for user {}", subscription.getUserId());
                } catch (Exception e) {
                    log.error(" Error sending block notification for {}: {}",
                            subscription.getUserId(), e.getMessage());
                }

                sendPaymentOverdueEmail(subscription);
            }
        }

        log.info(" Subscription check completed.");
    }
    private void sendTrialEndingReminderEmail(Subscription subscription) {
        String recipientEmail = getUserEmail(subscription.getUserId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Trial Period Ends Soon");
        message.setText(buildTrialEndingEmailBody(subscription));

        mailSender.send(message);
        log.info("Trial ending reminder sent to {}", recipientEmail);
    }

    private void sendPaymentOverdueEmail(Subscription subscription) {
        String recipientEmail = getUserEmail(subscription.getUserId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Payment Overdue - Account Access Restricted");
        message.setText(buildOverdueEmailBody(subscription));

        mailSender.send(message);
        log.info("Payment overdue notification sent to {}", recipientEmail);
    }

    private String buildTrialEndingEmailBody(Subscription subscription) {
        return String.format(
                """
                        Dear User,
                        
                        Your trial period for Plan # %s will end in 3 days (on %s).
                        To continue enjoying our services without interruption, please complete your payment before the trial ends.
                        Thank you,
                        
                        The Customer Support Team
                        """,
                subscription.getPlanId(),
                subscription.getTrialEndDate()
        );
    }

    private String buildOverdueEmailBody(Subscription subscription) {
        return String.format(
                """
                        Dear User,
                
                        We regret to inform you that your trial period for Plan # %s has ended and we haven't received your payment.
                
                        "As a result, your account access has been restricted. To restore access, please complete your payment at your earliest convenience.
                        "If you believe this is a mistake, please contact our support team.
                        "Best regards,
                
                        The Customer Support Team
                """,
                subscription.getPlanId()
        );
    }

    private boolean isSubscriptionUnpaid(Subscription subscription) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserIdAndStatusIn(
                subscription.getUserId(),
                List.of(
                        PaymentStatus.PENDING.toString(),
                        PaymentStatus.FAILED.toString(),
                        PaymentStatus.COMPLETED.toString()
                )
        );

        return subscriptions.stream()
                .noneMatch(p -> p.getStatus().equals(PaymentStatus.COMPLETED.name()));
    }

    // get emal from JWT token
    private String getUserEmail(String userId) {
        return "nouha.sayadi28@gmail.com";
    }

    public List<Subscription> getByStatus(String status) {
        return subscriptionRepository.findByStatus(status);
    }
    public Optional<Subscription> get(String id) {
        return subscriptionRepository.findById(id);
    }

    public void delete(String id) {
        subscriptionRepository.deleteById(id);
    }
}