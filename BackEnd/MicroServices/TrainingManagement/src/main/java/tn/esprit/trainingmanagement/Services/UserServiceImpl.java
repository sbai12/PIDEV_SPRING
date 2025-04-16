package tn.esprit.trainingmanagement.Services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.User;
import tn.esprit.trainingmanagement.Repository.UserRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User SaveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        return userRepo.findById(id).map(user -> {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepo.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);

    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
