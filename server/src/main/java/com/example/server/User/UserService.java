package com.example.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    //private final UserDAO userDAO;

    @Autowired //by autowiring spring knows that there is an implementation of FakeUserDAO or UserRepository somewhere
    //the qualifier annotation directs spring to the right implementation
    //we can replace the repo being called with FakeUserDAO and we simply need to refactor some method calls in this service class
    //allowing us to easily switch between different repositories
    public UserService(@Qualifier("database") UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.userDAO = userDAO;
    }

    //We will use an interface ('UserDAO' which is being included above) which allows us to load a user by a username from any data source
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .orElseThrow(()->{
                    System.out.println("Email not found");
                    throw new UsernameNotFoundException("Username not found");
                });
    }

    public User saveUser(Map<String, String> body){
        if(!body.containsKey("email") || !body.containsKey("password")){
            System.out.println("Poorly formatted request");
            return new User();
        }
        User existing = userRepository.findByEmail(body.get("email")).orElseThrow(()->{
            System.out.println("Email not found");
            throw new UsernameNotFoundException("Username not found");
        });
        if(existing != null) {
            System.out.println("Email already in use");
            return new User();
        }

        //create user
        return userRepository.save(new User(body.get("email"), body.get("password")));
    }


    public User findByEmail(String email) {
        User existing = userRepository.findByEmail(email).orElseThrow(()->{
            System.out.println("Email not found");
            throw new UsernameNotFoundException("Email not found");
        });
        if(existing == null) {
            System.out.println("No user found");
            return new User();
        }

        //return user
        return existing;
    }
}
