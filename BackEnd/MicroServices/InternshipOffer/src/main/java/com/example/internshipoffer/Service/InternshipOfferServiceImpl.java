package com.example.internshipoffer.Service;

import com.example.internshipoffer.Entity.Company;
import com.example.internshipoffer.Entity.InternshipOffer;
import com.example.internshipoffer.Entity.User;
import com.example.internshipoffer.Repository.CompanyRepository;
import com.example.internshipoffer.Repository.InternshipOfferRepository;
import com.example.internshipoffer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipOfferServiceImpl {

    @Autowired
     InternshipOfferRepository internshipOfferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;


    public InternshipOffer createInternshipOffer(InternshipOffer internshipOffer) {
        return internshipOfferRepository.save(internshipOffer);
    }
    public List<InternshipOffer> getAllInternshipOffers() {
        return internshipOfferRepository.findAll();
    }
    public Optional<InternshipOffer> getInternshipOfferById(Long id) {
        return internshipOfferRepository.findById(id);
    }
    public InternshipOffer updateInternshipOffer(Long id, InternshipOffer offerDetails) {
        return internshipOfferRepository.findById(id).map(offer -> {
            offer.setDescription(offerDetails.getDescription());
            offer.setCompetence(offerDetails.getCompetence());
            offer.setDuration(offerDetails.getDuration());
            offer.setType(offerDetails.getType());
            offer.setStatus(offerDetails.getStatus());
            offer.setCompanies(offerDetails.getCompanies());
            offer.setCandidates(offerDetails.getCandidates());
            offer.setRepresentant(offerDetails.getRepresentant());
            return internshipOfferRepository.save(offer);
        }).orElseThrow(() -> new RuntimeException("InternshipOffer non trouvé"));
    }
    public void deleteInternshipOffer(Long id) {
        internshipOfferRepository.deleteById(id);
}
//Affecter une offre de stage à un candidat
    public InternshipOffer assignCandidateToOffer(Long offerId, Long candidateId) {
        InternshipOffer offer = internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        offer.getCandidates().add(candidate); // Ajoute le candidat à la liste
        return internshipOfferRepository.save(offer);
    }

    public InternshipOffer assignCompanyToOffer(Long offerId, Long companyId) {
        InternshipOffer offer = internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));

        offer.getCompanies().add(company);
        return internshipOfferRepository.save(offer);
    }
    //changer statut offre
    public InternshipOffer updateOfferStatus(Long offerId, String newStatus) {
        InternshipOffer offer = internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        offer.setStatus(newStatus);
        return internshipOfferRepository.save(offer);
    }

}