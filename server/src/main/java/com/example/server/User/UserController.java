package com.example.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    //CREATE
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody Map<String, String> body){
        User userResult = userService.saveUser(body);
        return new ResponseEntity<>(userResult, HttpStatus.ACCEPTED);
    }

    //RETRIEVE
    @GetMapping("/retrieve/value={email}")
    public ResponseEntity<?> retrieveUser(@Valid @PathVariable String email){
        User userResult = userService.findByEmail(email);
        return new ResponseEntity<>(userResult, HttpStatus.ACCEPTED);
    }

}