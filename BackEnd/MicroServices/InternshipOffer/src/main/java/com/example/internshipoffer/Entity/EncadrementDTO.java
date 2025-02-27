package com.example.internshipoffer.Entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncadrementDTO {
     Long id;
    String status;
     String tache;
     LocalDate encadrement_date;
     Long teacherId;  // ID du professeur
     Long candidateId; // ID du candidat
}
