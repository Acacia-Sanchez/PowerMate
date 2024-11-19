package org.factoriaf5.powermate.controllers;

import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe", "johndoe@example.com", null);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {

        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void listUsers_shouldReturnListOfUsers() {
    
        List<User> users = Arrays.asList(user, new User(2L, "Jane Doe", "janedoe@example.com", null));
        when(userService.listUsers()).thenReturn(users);


        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        User updatedUser = new User(1L, "John Smith", "johnsmith@example.com", null);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(1L, updatedUser);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void updateUser_shouldReturnNotFoundWhenUserDoesNotExist() {

        when(userService.updateUser(eq(999L), any(User.class))).thenThrow(new RuntimeException("User not found"));


        ResponseEntity<User> response = userController.updateUser(999L, user);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_shouldReturnNoContentWhenUserIsDeleted() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void deleteUser_shouldReturnNotFoundWhenUserDoesNotExist() {
        when(userService.getUserById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userController.deleteUser(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}