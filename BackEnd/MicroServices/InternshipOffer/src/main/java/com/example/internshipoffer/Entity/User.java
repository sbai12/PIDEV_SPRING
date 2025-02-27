package com.example.internshipoffer.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     String firstname;
     String lastname;
     String password;
     String email;
     String specialite;
    String competence;
    String niveau;
     String phone;

    @Enumerated(EnumType.STRING)
     Role role;

    @OneToMany(mappedBy = "Teacher")
    List<Encadrement>encadrements;

    @OneToMany(mappedBy = "Candidate")
    List<Encadrement>encadrementSet;

    @OneToMany(mappedBy = "Representant")
    List<InternshipOffer>internshipOffers;
    @ManyToMany(mappedBy = "candidates")
    List<InternshipOffer> offres;




}
