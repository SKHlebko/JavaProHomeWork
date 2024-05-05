package org.javapro.skhlebko.homework_4.dao;

import lombok.extern.slf4j.Slf4j;
import org.javapro.skhlebko.homework_4.exception.UserNotFoundException;
import org.javapro.skhlebko.homework_4.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM users";
    private static final String INSERT_SQL = "INSERT INTO users (username) VALUES (?) RETURNING id";
    private static final String UPDATE_SQL = "UPDATE users SET username = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        return user;
    };

    @Override
    public Optional<User> findById(Long id) {
        try {
            log.info("Finding user by ID: {}", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found for ID: {}", id, e);
            throw new UserNotFoundException("User not found for ID: " + id);
        }
    }

    @Override
    public List<User> findAll() {
        log.info("Finding all users");
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper);
    }

    @Override
    public User save(User user) {
        log.info("Saving user: {}", user);
        Long id = jdbcTemplate.queryForObject(INSERT_SQL, (rs, rowNum) -> rs.getLong(1), user.getUsername());
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        log.info("Updating user: {}", user);
        jdbcTemplate.update(UPDATE_SQL, user.getUsername(), user.getId());
        return user;
    }

    @Override
    public Long delete(Long id) {
        findById(id);
        log.info("Deleting user by ID: {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        return id;
    }
}