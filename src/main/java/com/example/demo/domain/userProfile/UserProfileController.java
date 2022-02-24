package com.example.demo.domain.userProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileServiceImpl userProfileService;

    @PostMapping("/")
    @ResponseBody
    public void saveUserProfile(@RequestBody UserProfile userProfile) throws InstanceAlreadyExistsException {
        ResponseEntity.ok().body("Profile Created");
        userProfileService.saveUserProfile(userProfile);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUserProfile(@PathVariable String id) throws InstanceNotFoundException {
        ResponseEntity.ok().body("Profile Deleted");
        userProfileService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<UserProfile>> fetchUserProfiles(@PathVariable String id) {
        return new ResponseEntity<>(userProfileService.getUserProfile(id), HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Collection<UserProfile>> fetchAllUserProfiles() {
        return new ResponseEntity<Collection<UserProfile>>(userProfileService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateUserProfile() {
        return ResponseEntity.ok().body("Create not functioning");
    }
}
