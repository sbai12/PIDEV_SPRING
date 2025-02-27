package com.example.internshipoffer.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_offer;
    String description;
    String competence;
    String duration;
    String type;
    String status;

    @ManyToMany
    @JoinTable(
            name = "company_internship",
            joinColumns = @JoinColumn(name = "id_offer"),
            inverseJoinColumns = @JoinColumn(name = "id_company")
    )
    List<Company> companies;

    @ManyToMany
    @JoinTable(
            name = "candidate_internship",
            joinColumns = @JoinColumn(name = "id_offer"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    List<User> candidates;

@ManyToOne
    User Representant;

}
