package com.example.demo.domain.userProfile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController @RequestMapping("/api")
@RequiredArgsConstructor
public class    UserProfileController {
  //    ADD YOUR ENDPOINT MAPPINGS HERE
  private final UserProfileService userProfileService;

  @GetMapping("/userProfiles")
  public ResponseEntity<Collection<UserProfile>> findAll() {
    return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
  }

}
