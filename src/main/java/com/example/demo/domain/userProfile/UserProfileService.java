package com.example.demo.domain.userProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile saveUserProfile(UserProfile userProfile);

    UserProfile getUserProfile(String username);

    UserProfile updateUserProfile(UserProfile userProfile, String username);

    String deleteById(String username);

    List<UserProfile> findAllWithPagination(Integer page, Integer valuesPerPage);

    List<UserProfile> findAll();
}
