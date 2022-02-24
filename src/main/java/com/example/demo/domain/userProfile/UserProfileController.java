package com.example.demo.domain.userProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileServiceImpl userProfileService;

    @PostMapping("/")
    @ResponseBody
    public void saveUserProfile(@RequestBody UserProfile userProfile) {
        ResponseEntity.ok().body("Profile Created");
        userProfileService.saveUserProfile(userProfile);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUserProfile(@PathVariable UUID id) throws InstanceNotFoundException {
        ResponseEntity.ok().body("Profile Deleted");
        userProfileService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<UserProfile>> fetchUserProfiles(@PathVariable UUID id) {
        return new ResponseEntity<>(userProfileService.getUserProfile(id), HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Collection<UserProfile>> fetchAllUserProfiles() {
        return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
    }

    //Return Type not quite sure yet
    @PutMapping("/{id}")
    @ResponseBody
    public UserProfile updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable UUID id) {
        return userProfileService.getUserProfile(id).map( foundUserProfile -> {
            foundUserProfile.setUser(userProfile.getUser());
            foundUserProfile.setBiography(userProfile.getBiography());
            foundUserProfile.setLocation(userProfile.getLocation());
            foundUserProfile.setDateOfBirth(userProfile.getDateOfBirth());
            foundUserProfile.setId(userProfile.getId());
            return userProfileService.saveUserProfile(userProfile);
                }).orElseGet(() -> userProfileService.saveUserProfile(userProfile)
                );
    }
}
