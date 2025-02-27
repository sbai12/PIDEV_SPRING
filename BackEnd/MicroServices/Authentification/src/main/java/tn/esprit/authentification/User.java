package tn.esprit.authentification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    @Setter
    @Entity
    @Table(name = "users")
    public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String password;
        private String role; // Exemple : "USER" ou "ADMIN"
    }
}
