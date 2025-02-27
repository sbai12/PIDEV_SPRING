package com.example.internshipoffer.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_company;
    String phone;
    String address;
    String description;

    @ManyToMany(mappedBy = "companies")
    List<InternshipOffer> internshipOffers;

}
