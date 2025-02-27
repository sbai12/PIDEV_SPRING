package com.example.internshipoffer.Entity;

import com.example.internshipoffer.Entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
     Long id;
     String firstname;
    String lastname;
    String email;
     String phone;
   String specialite;
     String competence;
    String niveau;
     Role role;
}

