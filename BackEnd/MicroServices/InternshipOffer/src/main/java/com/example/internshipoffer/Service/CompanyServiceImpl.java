package com.example.internshipoffer.Service;

import com.example.internshipoffer.Dto.CompanyInternshipCountDTO;
import com.example.internshipoffer.Entity.Company;
import com.example.internshipoffer.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl {

    @Autowired
     CompanyRepository companyRepository;

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }
    public Company updateCompany(Long id, Company companyDetails) {
        return companyRepository.findById(id).map(company -> {
            company.setPhone(companyDetails.getPhone());
            company.setAddress(companyDetails.getAddress());
            company.setDescription(companyDetails.getDescription());
            return companyRepository.save(company);
        }).orElseThrow(() -> new RuntimeException("Company not found"));
    }
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }


    public List<CompanyInternshipCountDTO> getInternshipCountsPerCompany() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(company -> new CompanyInternshipCountDTO(
                        company.getDescription(), // Assuming description is company name
                        (long) company.getInternshipOffers().size()
                ))
                .collect(Collectors.toList());
    }



}
