package org.javapro.skhlebko.homework_4;

import lombok.extern.slf4j.Slf4j;
import org.javapro.skhlebko.homework_2.utils.Utils;
import org.javapro.skhlebko.homework_4.config.AppConfig;
import org.javapro.skhlebko.homework_4.model.User;
import org.javapro.skhlebko.homework_4.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
@Slf4j
public class MainApp {

    public static void main(String[] args) {
        log.info("Приложение запущено");
        Utils.printFormatted('.', 60);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Получение бина UserService
            UserService userService = context.getBean(UserService.class);

            // Создание нового пользователя
            User newUser = new User(null, "newUser");
            newUser = userService.createUser(newUser);

            System.out.println("User created: " + newUser);

            // Получение пользователя по ID
            User finalNewUser = newUser;
            Utils.printFormatted('.', 60);
            userService.getUserById(newUser.getId()).ifPresentOrElse(
                    user -> System.out.println("User retrieved: " + user),
                    () -> System.out.println("User not found with ID: " + finalNewUser.getId())
            );

            // Получение всех пользователей
            Utils.printFormatted('.', 60);
            System.out.println("All users: ");
            userService.getAllUsers().forEach(System.out::println);

            // Удаление пользователя
            Utils.printFormatted('.', 60);
            userService.deleteUser(newUser.getId());
            System.out.println("User deleted");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}