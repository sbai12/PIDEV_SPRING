package com.example.internshipoffer.Repository;



import com.example.internshipoffer.Entity.Role;
import com.example.internshipoffer.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
}

