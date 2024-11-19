package org.factoriaf5.powermate.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        
        user = User.builder()
                    .id(1L)
                    .username("testuser")
                    .password("password123")
                    .role("USER")
                    .build();
    }

    @Test
    public void testUserCreation() {
       
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testUserId() {
        
        assertEquals(1L, user.getId());
    }

    @Test
    public void testUserRole() {
        
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testUserSettersAndGetters() {
        
        user.setUsername("newuser");
        user.setPassword("newpassword123");
        user.setRole("ADMIN");

        assertEquals("newuser", user.getUsername());
        assertEquals("newpassword123", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    public void testUserNoArgsConstructor() {
        
        User emptyUser = new User();
        assertNotNull(emptyUser);
    }
}
