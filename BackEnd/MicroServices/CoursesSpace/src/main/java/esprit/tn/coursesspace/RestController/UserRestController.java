package esprit.tn.coursesspace.RestController;


import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/api/formations")
@AllArgsConstructor

@Slf4j

public class UserRestController {
   @Autowired
    IUserService userService;
    @GetMapping
    public List<String> getFormations() {
        return Arrays.asList("Spring Boot", "Angular", "Docker");
    }
    @PostMapping("/add-User")
    User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/getall-User")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("get-User/{idUser}")
    User getUserById(@PathVariable("idUser") long user_id) {
        return userService.getUserById(user_id);
    }

    @DeleteMapping("/delete-User/{idUser}")
    void deleteUser(@PathVariable("idUser") long user_id) {
        userService.deleteUser(user_id);
    }

   @PutMapping("/update-User")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
       if (user.getId_user() == null) {
            return ResponseEntity.badRequest().body("L'ID de l'utilisateur est obligatoire pour la mise à jour.");
        }

        try {
          User updatedUser = userService.updateUser(user);
           return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }

    }
}
