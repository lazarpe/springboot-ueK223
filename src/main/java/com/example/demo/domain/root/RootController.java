package com.example.demo.domain.root;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RootController {

    @Operation(summary = "Endpoint used as a landing page for displaying greetings text.")
    @GetMapping("")
    public ResponseEntity<String> testRoot() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
