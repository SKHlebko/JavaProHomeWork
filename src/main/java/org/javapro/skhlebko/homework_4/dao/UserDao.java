package org.javapro.skhlebko.homework_4.dao;

import org.javapro.skhlebko.homework_4.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}