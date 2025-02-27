package com.example.internshipoffer.Service;

import com.example.internshipoffer.Entity.Encadrement;
import com.example.internshipoffer.Repository.EncadrementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EncadrementServiceImpl {

    @Autowired
     EncadrementRepository encadrementRepository;

    public Encadrement createEncadrement(Encadrement encadrement) {
        return encadrementRepository.save(encadrement);
    }
    public List<Encadrement> getAllEncadrements() {
        return encadrementRepository.findAll();
    }


    public Optional<Encadrement> getEncadrementById(Long id) {
        return encadrementRepository.findById(id);
    }
    public Encadrement updateEncadrement(Long id, Encadrement encadrementDetails) {
        return encadrementRepository.findById(id).map(encadrement -> {
            encadrement.setStatus(encadrementDetails.getStatus());
            encadrement.setTache(encadrementDetails.getTache());
            encadrement.setEncadrement_date(encadrementDetails.getEncadrement_date());
            encadrement.setTeacher(encadrementDetails.getTeacher());
            encadrement.setCandidate(encadrementDetails.getCandidate());
            return encadrementRepository.save(encadrement);
        }).orElseThrow(() -> new RuntimeException("Encadrement non trouvé"));
    }
    public void deleteEncadrement(Long id) {
        encadrementRepository.deleteById(id);
    }
}
