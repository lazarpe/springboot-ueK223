package com.example.demo.domain.userProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/userProfile")
public class UserProfileController {

    @Autowired
    private UserProfileServiceImpl userProfileService;

    // Create user profile  + Not functioning
    @PostMapping("/create") /* Mapping must get changed */
    @ResponseBody
    public ResponseEntity<String> saveUserProfile(){
        return ResponseEntity.ok().body("Create not functioning");
    }

    // Delete user profile
    @DeleteMapping("/delete/{id}") /* Mapping must get changed */
    @ResponseBody
    public ResponseEntity<Void> deleteUserProfile(@PathVariable String id){
        return new ResponseEntity<Void>()userProfileService.deleteById(id);, HttpStatus.OK);
    }

    // Get single user profile
    @GetMapping("/get/{id}") /* Mapping must get changed */
    @ResponseBody
    public ResponseEntity<Optional<UserProfile>> fetchUserProfiles(@PathVariable String id){
        return new ResponseEntity<>(userProfileService.getUserProfile(id), HttpStatus.OK);
    }

    // Get all user profile
    @GetMapping("/getAll") /* Mapping must get changed */
    @ResponseBody
    public ResponseEntity<Collection<UserProfile>> fetchAllUserProfiles(){
        return new ResponseEntity<Collection<UserProfile>>(userProfileService.findAll(), HttpStatus.OK);
    }

    // Update user profile  + Not functioning
    @PutMapping("/update/{id}/") /* Mapping must get changed */
    @ResponseBody
    public ResponseEntity<String> updateUserProfile(){
        return ResponseEntity.ok().body("Create not functioning");
    }
}
