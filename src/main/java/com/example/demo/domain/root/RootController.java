package com.example.demo.domain.root;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api")
public class RootController {

  @GetMapping("")
  public ResponseEntity<String> testRoot() {
    return new ResponseEntity<>("Hello World!", HttpStatus.OK);
  }
}
