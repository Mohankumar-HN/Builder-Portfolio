package com.zeta;

import com.zeta.console.App;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.User;
import com.zeta.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class TestAuthService {
    AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        App.users.add(new User("1", "Pragnya", "1234", ROLE_TYPE.PROJECTMANAGER));
    }
    @Test
    public void testRegisterNewUser() {
        boolean exists = authService.register("1");
        assertTrue(exists);
    }
    @Test
    public void testRegisterDuplicateUser() {
        boolean exists = authService.register("1");
        assertTrue(exists);
    }
    @Test
    public void testLoginSuccess() {
        User user = authService.logIn("Pragnya", "1234");
        assertNotNull(user);
        assertEquals("Pragnya", user.getName());
    }
    @Test
    public void testLoginWrongPassword() {
        User user = authService.logIn("Pragnya", "0000");
        assertNull(user);
    }
    @Test
    public void testLoginWrongUserName(){
        User user=authService.logIn("pragna","1234");
        assertNull(user);
    }
    @Test
    public void testLoginMultipleUsers() {
        App.users.add(new User("2", "Ravi", "abcd", ROLE_TYPE.BUILDER));
        User otherUser = authService.logIn("Ravi", "abcd");
        assertNotNull(otherUser);
        assertEquals(ROLE_TYPE.BUILDER, otherUser.getRole());
    }
}
