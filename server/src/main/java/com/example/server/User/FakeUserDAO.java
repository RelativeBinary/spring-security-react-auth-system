package com.example.server.User;

import java.util.Optional;

//TODO THIS HAS NOTHING TO DO WITH THE ACTUAL OPERATION OF THIS PROGRAM THIS IS A DUMMY INTERFACE
public interface FakeUserDAO {
    Optional<User> selectFakeUserByUsername(String username);
}
