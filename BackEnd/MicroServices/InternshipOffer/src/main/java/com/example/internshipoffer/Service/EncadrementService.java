package com.example.internshipoffer.Service;

import com.example.internshipoffer.Entity.Encadrement;

import java.util.List;
import java.util.Optional;

public interface EncadrementService {
    Encadrement createEncadrement(Encadrement encadrement);
    List<Encadrement> getAllEncadrements();
    Optional<Encadrement> getEncadrementById(Long id);
    Encadrement updateEncadrement(Long id, Encadrement encadrementDetails);
    void deleteEncadrement(Long id);
}
