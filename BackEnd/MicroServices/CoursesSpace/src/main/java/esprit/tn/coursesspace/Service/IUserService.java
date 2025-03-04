package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(long user_id);
    void deleteUser(long user_id);

    User updateUser(User user);




}
