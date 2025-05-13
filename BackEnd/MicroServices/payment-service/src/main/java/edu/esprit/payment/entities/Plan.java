package edu.esprit.payment.entities;

import edu.esprit.payment.enums.PlanType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Plan {
    @Id
    private String id;
    private String name;
    private String description;
    private Integer amount;
    private String currency;
    private PlanType type;
    private String productId;
    private String paypalPlanId;
}
