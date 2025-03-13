package esprit.tn.coursesspace.Repository;


import esprit.tn.coursesspace.Entity.Role;
import esprit.tn.coursesspace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Vérifie que cette méthode existe
    List<User> findByRole(Role role);


}
