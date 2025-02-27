package com.example.internshipoffer.Entity;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    Long id_company;
     String phone;
     String address;
     String description;
     List<Long> internshipOfferIds; // Liste des IDs des offres de stage associées à cette entreprise
}

