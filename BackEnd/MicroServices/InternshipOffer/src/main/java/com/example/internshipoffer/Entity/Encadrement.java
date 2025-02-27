package com.example.internshipoffer.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encadrement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String status;
    String tache;
    LocalDate encadrement_date;

    @ManyToOne
    User Teacher;
    @ManyToOne
    User Candidate;
}
