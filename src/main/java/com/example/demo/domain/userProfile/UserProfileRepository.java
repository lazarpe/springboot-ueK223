package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by lazar on 2/23/2022.
 * Project name: demo
 **/
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
}
