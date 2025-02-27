package com.example.internshipoffer.RestController;
import com.example.internshipoffer.Entity.Company;
import com.example.internshipoffer.Service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyRestController {

    @Autowired
    CompanyServiceImpl companyServiceImpl;

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyServiceImpl.addCompany(company);
    }
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyServiceImpl.getAllCompanies();
    }
    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) {
        return companyServiceImpl.getCompanyById(id);
    }
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyServiceImpl.updateCompany(id, company);
    }
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyServiceImpl.deleteCompany(id);
    }
}
