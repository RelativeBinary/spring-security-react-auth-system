package com.example.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//TODO THIS HAS NOTHING TO DO WITH THE ACTUAL OPERATION OF THIS PROGRAM THIS IS A DUMMY CLASS
@Repository("fake") //this tells spring that this class needs to be instantiated, and this name will identify this particular repository implementation of UserDAO
public class FakeUserDaoService implements FakeUserDAO {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectFakeUserByUsername(String username) {
        return getFakeUsers()
                .stream()
                .filter(user -> {
                    return username.equals(user.getEmail());
                })
                .findFirst();
    }

    private List<User> getFakeUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("beth@email.com", passwordEncoder.encode("password")));
        users.add(new User("summer@email.com", passwordEncoder.encode("password")));
        users.add(new User("jerry@email.com", passwordEncoder.encode("password")));

        return users;
    }

}
