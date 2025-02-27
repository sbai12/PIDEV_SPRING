package com.example.internshipoffer.Repository;

import com.example.internshipoffer.Entity.Encadrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncadrementRepository extends JpaRepository<Encadrement, Long> {

}
