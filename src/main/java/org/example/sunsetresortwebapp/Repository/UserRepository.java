package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository <User,Long> {
        User findUserByEmail(String email);
        User findUserById(Long id);
}
