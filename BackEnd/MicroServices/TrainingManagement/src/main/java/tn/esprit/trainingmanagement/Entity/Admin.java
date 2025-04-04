package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.trainingmanagement.Entity.FctsAv.AdminType;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Admin extends User {

    String specialization;


    @Enumerated(EnumType.STRING)
    AdminType adminType;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Training> trainings;


}


