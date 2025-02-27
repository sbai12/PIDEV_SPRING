package com.example.internshipoffer.Service;

import com.example.internshipoffer.Entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company addCompany(Company company);
    List<Company> getAllCompanies();
    Optional<Company> getCompanyById(Long id);
    Company updateCompany(Long id, Company companyDetails);
    void deleteCompany(Long id);
}
