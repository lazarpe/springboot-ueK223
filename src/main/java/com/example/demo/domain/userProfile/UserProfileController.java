package com.example.demo.domain.userprofile;

import com.example.demo.domain.appUser.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        userProfileService.saveUserProfile(userProfile);
        ResponseEntity.ok().body("Profile Created");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUserProfile(@PathVariable UUID id) throws InstanceNotFoundException {
        userProfileService.deleteById(id);
        ResponseEntity.ok().body("Profile Deleted");
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
    public void updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable UUID id) {
        userProfileService.updateUserProfile(userProfile, id);
        ResponseEntity.ok().body("User profile updated");
    }
}
