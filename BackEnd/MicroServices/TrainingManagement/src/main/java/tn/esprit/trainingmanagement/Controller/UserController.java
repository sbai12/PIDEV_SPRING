package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.Admin;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.User;
import tn.esprit.trainingmanagement.Services.UserServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping("/register")
    public User SaveUser(@RequestBody User user) {

        return userService.SaveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }






    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("You are not registered. Please sign up.");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials.");
        }

        if (user instanceof Admin) {
            return ResponseEntity.ok(Map.of("role", "ADMIN", "id", user.getId()));
        } else if (user instanceof Student) {
            return ResponseEntity.ok(Map.of("role", "STUDENT", "id", user.getId()));
        } else {
            return ResponseEntity.ok(Map.of("role", "USER", "id", user.getId()));
        }
    }


}

