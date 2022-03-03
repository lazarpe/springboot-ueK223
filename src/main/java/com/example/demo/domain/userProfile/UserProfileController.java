package com.example.demo.domain.userProfile;

import com.example.demo.domain.security.UserSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserSecurity userSecurity;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileServiceImpl userProfileService;

    /**
     * Save new user to db using hibernate
     *
     * @param userProfile passed JSON object from request
     * @return ResponseEntity with new userprofile and http status
     */
    @Operation(summary = "Endpoint for creating and saving given userProfiles in the body.")
    @PostMapping("/")
    public ResponseEntity<UserProfile> saveUserProfile(@Valid @RequestBody UserProfile userProfile) {
        UserProfile createdUserProfile = userProfileService.saveUserProfile(userProfile);
        return new ResponseEntity<>(createdUserProfile, HttpStatus.CREATED);
    }

    /**
     * delete userprofile by it's username (everyone with an account owning the default role)
     *
     * @param username variable from url path of request
     */
    @Operation(summary = "Endpoint for deleting a userProfile with a given.")
    @PreAuthorize("hasRole('ADMIN')")
    // => Only an admin can delete a profile since they don't explicitly need one. However, a user can only delete them in userController and in that operation their profile will additionally be deleted.
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable String username) {
        return new ResponseEntity<>(userProfileService.deleteById(username), HttpStatus.ACCEPTED);
    }

    /**
     * Any user can see profiles of others. Admin role users can see all secret backend information and all the others
     * are viewing a DTO of PublicUserProfileDTO
     *
     * @param username value from request path
     * @return
     */
    @Operation(summary = "Endpoint for finding a specific user by username.")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEFAULT')")
    @GetMapping("/{username}")
    public ResponseEntity<Object> findByUsername(@PathVariable String username) {
        if (userSecurity.hasRole(userSecurity.getCurrentlyLoggedInUser(), "ROLE_ADMIN")) {
            return new ResponseEntity<>(
                    userProfileService.getUserProfile(username)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    userProfileMapper.convertUserProfileToPublicUserProfileDTO(userProfileService.getUserProfile(username))
                    , HttpStatus.OK);
        }
    }

    @Operation(summary = "Endpoint for only administrators, which displays all user profiles with pagination and sorting by username.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{page}/{valuesPerPage}")
    public ResponseEntity<Collection<UserProfile>> findAllUserProfilesPagination(@PathVariable Integer page, @PathVariable Integer valuesPerPage) {
        return new ResponseEntity<>(userProfileService.findAllWithPagination(page, valuesPerPage), HttpStatus.OK);
    }

    @Operation(summary = "Endpoint for only administrators, which displays all user profiles.")
//add parameters to all swagger stuff
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Collection<UserProfile>> findAllUserProfiles() {
        return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Endpoint for editing a user profile.")
    @PutMapping("/{username}")
    public ResponseEntity<UserProfile> updateUserProfile(@Valid @RequestBody UserProfile userProfile, @PathVariable String username) {
        return new ResponseEntity<>(userProfileService.updateUserProfile(userProfile, username), HttpStatus.OK);
    }
}
