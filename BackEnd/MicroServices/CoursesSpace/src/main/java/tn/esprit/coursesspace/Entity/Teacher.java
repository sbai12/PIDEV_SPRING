package tn.esprit.coursesspace.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Teacher extends User {
     String specialty;
     int experience;
     float rating;
}

