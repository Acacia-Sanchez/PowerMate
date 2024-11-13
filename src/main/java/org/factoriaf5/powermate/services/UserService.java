package org.factoriaf5.powermate.services;

import org.factoriaf5.powermate.models.User;

import org.factoriaf5.powermate.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Crear un nuevo usuario
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    // Optional es contenedor para valores que pueden estar o no presentes (null)

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Actualizar un usuario existente
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // Eliminar un usuario por ID
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

}