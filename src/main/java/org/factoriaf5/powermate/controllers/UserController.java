package org.factoriaf5.powermate.controllers;

import java.util.List;

import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Crear un nuevo usuario
    @PostMapping(path = "/admin/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 2. Obtener un usuario por su ID
    /*
     * @GetMapping("/users/{id}")
     * public ResponseEntity<User> getUserById(@PathVariable Long id) {
     * Optional<User> user = userService.getUserById(id);
     * return user.map(ResponseEntity::ok)
     * .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
     * .body(null));
     * }
     */

    // 3. Obtener todos los usuarios
    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    // 4. Actualizar un usuario existente
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // 5. Eliminar un usuario
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}