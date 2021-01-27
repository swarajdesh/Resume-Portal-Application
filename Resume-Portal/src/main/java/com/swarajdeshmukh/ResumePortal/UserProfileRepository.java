package com.swarajdeshmukh.resumeportal;

import com.swarajdeshmukh.resumeportal.models.User;
import com.swarajdeshmukh.resumeportal.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByuserName(String userName);
}
