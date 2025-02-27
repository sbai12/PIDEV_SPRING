package com.example.internshipoffer.RestController;

import com.example.internshipoffer.Entity.InternshipOffer;
import com.example.internshipoffer.Service.InternshipOfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/internship-offers")
 public class InternshiOfferController {

    @Autowired
    InternshipOfferServiceImpl internshipOfferServiceImpl;
    @PostMapping
    public InternshipOffer createInternshipOffer(@RequestBody InternshipOffer internshipOffer) {
        return internshipOfferServiceImpl.createInternshipOffer(internshipOffer);
    }
    @GetMapping
    public List<InternshipOffer> getAllInternshipOffers() {
        return internshipOfferServiceImpl.getAllInternshipOffers();
    }
    @GetMapping("/{id}")
    public Optional<InternshipOffer> getInternshipOfferById(@PathVariable Long id) {
        return internshipOfferServiceImpl.getInternshipOfferById(id);
    }
    @PutMapping("/{id}")
    public InternshipOffer updateInternshipOffer(@PathVariable Long id, @RequestBody InternshipOffer internshipOffer) {
        return internshipOfferServiceImpl.updateInternshipOffer(id, internshipOffer);
    }

    @DeleteMapping("/{id}")
    public void deleteInternshipOffer(@PathVariable Long id) {
        internshipOfferServiceImpl.deleteInternshipOffer(id);
    }
    // Affecter une offre de stage à un candidat
    @PutMapping("/{offerId}/assign-candidate/{candidateId}")
    public InternshipOffer assignCandidate(@PathVariable Long offerId, @PathVariable Long candidateId) {
        return internshipOfferServiceImpl.assignCandidateToOffer(offerId, candidateId);
    }
    //Affecter une offre à une entreprise
    @PutMapping("/{offerId}/assign-company/{companyId}")
    public InternshipOffer assignCompany(@PathVariable Long offerId, @PathVariable Long companyId) {
        return internshipOfferServiceImpl.assignCompanyToOffer(offerId, companyId);
    }
    //changer statut offre
    @PutMapping("/{offerId}/update-status")
    public InternshipOffer updateOfferStatus(@PathVariable Long offerId, @RequestParam String newStatus) {
        return internshipOfferServiceImpl.updateOfferStatus(offerId, newStatus);
    }

}
