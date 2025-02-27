package tn.esprit.authentification;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {


        @GetMapping("/public")
        public String publicEndpoint() {
            return "Accessible par tout le monde";
        }

        @GetMapping("/user")
        @PreAuthorize("hasRole('USER')")
        public String userEndpoint() {
            return "Accessible uniquement aux utilisateurs avec le rôle USER";
        }

        @GetMapping("/admin")
        @PreAuthorize("hasRole('ADMIN')")
        public String adminEndpoint() {
            return "Accessible uniquement aux administrateurs";
        }
    }

