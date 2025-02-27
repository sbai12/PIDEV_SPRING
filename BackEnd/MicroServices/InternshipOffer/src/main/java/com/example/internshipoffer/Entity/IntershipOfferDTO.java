package com.example.internshipoffer.Entity;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntershipOfferDTO {
    Long id_offer;
    String description;
     String competence;
     String duration;
     String type;
     String status;
     List<Long> companyIds; // Liste des IDs des entreprises
     List<Long> candidateIds; // Liste des IDs des candidats
     Long representantId;  // ID du représentant
}

