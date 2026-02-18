package com.zeta;

import com.zeta.Dao.UserDao;
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
        UserDao.users.add(new User("1", "Pragnya", "1234", ROLE_TYPE.PROJECTMANAGER));
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
        assertEquals("Pragnya", user.getUserName());
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
        UserDao.users.add(new User("2", "mohan", "abcd", ROLE_TYPE.BUILDER));
        User otherUser = authService.logIn("mohan", "abcd");
        assertNotNull(otherUser);
        assertEquals(ROLE_TYPE.BUILDER, otherUser.getRole());
    }

    @Test
    public void testIsValidPassword(){
        boolean result=authService.isValidPassword("Mohan@12345");
        assertEquals(true,result);
    }

    @Test
    public void testIsNotValidPassword(){
        boolean result=authService.isValidPassword("1234");
        assertEquals(false,result);
    }

    @Test
    public void testUserNameExistence(){
        boolean result=authService.usernameExists("pragnyak");
        assertEquals(false,result);
    }

    @Test
    public  void testForisValidtext(){
        boolean result=authService.isValidText("1234");
        assertEquals(false,result);
    }

    @Test
    public  void testForisValidtextpresent(){
        boolean result=authService.isValidText("abcd");
        assertEquals(true,result);
    }

    @Test
    public void testgetbyusername(){
        User result=authService.getUserByName("Pragnya");
        assertEquals("Pragnya",result.getUserName());
    }



}
