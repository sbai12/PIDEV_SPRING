package com.example.internshipoffer.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    List<InternshipOffer> internshipOffers;

}
