package com.example.demo.domain.userprofile;

import io.swagger.v3.oas.annotations.Operation;
//import com.example.demo.domain.appUser.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileServiceImpl userProfileService;

    @Operation(summary="Endpoint for creating and saving given userProfiles in the body.")
    @PostMapping("/")
    public ResponseEntity<UserProfile> saveUserProfile(@Valid @RequestBody UserProfile userProfile) {
        UserProfile createdUserProfile = userProfileService.saveUserProfile(userProfile);
        return new ResponseEntity<>(createdUserProfile, HttpStatus.CREATED);
    }

    @Operation(summary="Endpoint for deleting a userProfile with a given.")
    @DeleteMapping("/{username}")
    public void deleteUserProfile(@PathVariable String username) throws InstanceNotFoundException {
        userProfileService.deleteById(username);
        ResponseEntity.ok().body("Profile Deleted");
    }

    @Operation(summary="Endpoint for finding a specific user by username.")
    @GetMapping("/{username}")
    public ResponseEntity<Optional<UserProfile>> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userProfileService.getUserProfile(username), HttpStatus.OK);
    }

    @Operation(summary="Endpoint for only administrators, which displays all user profiles.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Collection<UserProfile>> findAllUserProfiles() {
        return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
    }

    @Operation(summary="Endpoint for editing a user profile.")
    @PutMapping("/{username}")
    public void updateUserProfile(@Valid @RequestBody UserProfile userProfile, @PathVariable String username) {
        userProfileService.updateUserProfile(userProfile, username);
        ResponseEntity.ok().body("User profile updated");
    }
}
