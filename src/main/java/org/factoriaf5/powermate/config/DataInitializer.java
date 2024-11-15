package org.factoriaf5.powermate.config;

import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        try {

            if (!userRepository.existsByUsername("root")) {

                User root = new User();
                root.setUsername("root");
                root.setRole("ADMIN");
                root.setPassword(passwordEncoder.encode("1234"));
                userRepository.save(root);
                System.out.println("usuario root creado en la base de datos.");

            } else {
                System.out.println("usuario root cargado.");
            }

        } catch (Exception e) {

            System.out.println("Error de creación");

        }

    }

}
