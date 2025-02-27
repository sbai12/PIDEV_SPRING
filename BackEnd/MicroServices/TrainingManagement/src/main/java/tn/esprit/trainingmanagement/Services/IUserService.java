package tn.esprit.trainingmanagement.Services;

import tn.esprit.trainingmanagement.Entity.User;

import java.util.List;

public interface IUserService {
    User SaveUser(User user);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
}
