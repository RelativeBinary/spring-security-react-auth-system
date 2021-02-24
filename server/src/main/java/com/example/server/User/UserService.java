package com.example.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(Map<String, String> body){
        if(!body.containsKey("email") || !body.containsKey("password")){
            System.out.println("Poorly formatted request");
            return new User();
        }
        User existing = userRepository.findByEmail(body.get("email"));
        if(existing != null) {
            System.out.println("Email already in use");
            return new User();
        }

        //create user
        return userRepository.save(new User(body.get("email"), body.get("password")));
    }


    public User findByEmail(String email) {
        User existing = userRepository.findByEmail(email);
        if(existing == null) {
            System.out.println("No user found");
            return new User();
        }

        //return user
        return existing;
    }


}
