package tn.esprit.coursesspace.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Candidate extends User {
     int age;
     String preferences;
     int progress;
     boolean certification;
     int result;
     String feedback;
}

