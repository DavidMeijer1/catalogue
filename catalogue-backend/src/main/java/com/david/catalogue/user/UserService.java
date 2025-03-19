package com.david.catalogue.user;

import com.david.catalogue.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);
}
