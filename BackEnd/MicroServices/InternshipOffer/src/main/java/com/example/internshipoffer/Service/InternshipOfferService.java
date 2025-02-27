package com.example.internshipoffer.Service;

import com.example.internshipoffer.Entity.InternshipOffer;

import java.util.List;
import java.util.Optional;

public interface InternshipOfferService {
    InternshipOffer createInternshipOffer(InternshipOffer internshipOffer);
    List<InternshipOffer> getAllInternshipOffers();
    Optional<InternshipOffer> getInternshipOfferById(Long id);
    InternshipOffer updateInternshipOffer(Long id, InternshipOffer offerDetails);
    void deleteInternshipOffer(Long id);
    InternshipOffer assignCandidateToOffer(Long offerId, Long candidateId);
}
