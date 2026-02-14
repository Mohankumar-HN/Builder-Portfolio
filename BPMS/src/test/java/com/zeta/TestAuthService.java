package com.zeta;

import com.zeta.entity.User;
import com.zeta.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class TestAuthService {

    AuthService authService;
    @BeforeEach
    void setup(){
        authService=new AuthService();
    }

    @Test
    void testForIncorrectUsername(){
        User result=authService.logIn("JOHN","234");
        assertEquals(true,result);
    }




}
