package com.example.demo.domain.userProfile;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

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
    @PreAuthorize("hasRole('ADMIN')") // => Only an admin can delete a profile since they don't explicitly need one. However, a user can only delete them in userController and in that operation their profile will additionally be deleted.
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable String username) {
        return new ResponseEntity<>(userProfileService.deleteById(username), HttpStatus.ACCEPTED);
    }

    @Operation(summary="Endpoint for finding a specific user by username.")
    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userProfileService.getUserProfile(username), HttpStatus.OK);
    }

    @Operation(summary="Endpoint for only administrators, which displays all user profiles with pagination and sorting by username.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{page}/{valuesPerPage}")
    public ResponseEntity<Collection<UserProfile>> findAllUserProfilesPagination(@PathVariable Integer page, @PathVariable Integer valuesPerPage) {
        return new ResponseEntity<>(userProfileService.findAllWithPagination(page, valuesPerPage), HttpStatus.OK);
    }

    @Operation(summary="Endpoint for only administrators, which displays all user profiles.")//add parameters to all swagger stuff
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Collection<UserProfile>> findAllUserProfiles() {
        return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
    }

    @Operation(summary="Endpoint for editing a user profile.")
    @PutMapping("/{username}")
    public ResponseEntity<UserProfile> updateUserProfile(@Valid @RequestBody UserProfile userProfile, @PathVariable String username) {
        return new ResponseEntity<>(userProfileService.updateUserProfile(userProfile, username), HttpStatus.OK);
    }
}
