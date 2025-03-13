package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Repository.ICourseRepository;
import esprit.tn.coursesspace.Repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public void deleteUser(long user_id) {
        userRepository.deleteById(user_id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.findById(user.getId_user())
                .map(existingUser -> {
                   existingUser.setUsername(
                           user.getUsername());
                   existingUser.setEmail(user.getEmail());
                   existingUser.setPassword(user.getPassword());
                   return userRepository.save(existingUser);
               })
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + user.getId_user()));

    }


}





