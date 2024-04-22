package org.javapro.skhlebko.homework_4.service;

import org.javapro.skhlebko.homework_4.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
}