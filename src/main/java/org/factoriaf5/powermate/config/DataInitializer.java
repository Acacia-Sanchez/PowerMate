package org.factoriaf5.powermate.config;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
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

            if (!deviceRepository.existsByName("rootDevice")) {

                Device rootDevice = new Device();
                rootDevice.setUser(userRepository.findByUsername("root").orElse(null));
                rootDevice.setName("rootDevice");
                rootDevice.setStatus(false);
                rootDevice.setPower(0);
                deviceRepository.save(rootDevice);
                System.out.println("dispositivo root creado en la base de datos.");

            } else {
                System.out.println("dispositivo root cargado.");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
