package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile,Long> {
    UserProfile findUserProfileByUserId(Long userId);
}
