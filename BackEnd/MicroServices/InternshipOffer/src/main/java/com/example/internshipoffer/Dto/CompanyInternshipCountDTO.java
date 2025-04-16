package com.example.internshipoffer.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyInternshipCountDTO {
    private String companyName;
    private Long internshipCount;
}
