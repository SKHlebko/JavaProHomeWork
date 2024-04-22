package org.javapro.skhlebko.homework_4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//import org.javapro.skhlebko.homework_4.dao.UserDao;
//import org.javapro.skhlebko.homework_4.dao.UserDaoImpl;
//import org.javapro.skhlebko.homework_4.service.UserService;
//import org.javapro.skhlebko.homework_4.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean
//    public UserDao userDao(JdbcTemplate jdbcTemplate) {
//        return new UserDaoImpl(jdbcTemplate);
//    }
//
//    @Bean
//    public UserService userService(UserDao userDao) {
//        return new UserServiceImpl(userDao);
//    }
}