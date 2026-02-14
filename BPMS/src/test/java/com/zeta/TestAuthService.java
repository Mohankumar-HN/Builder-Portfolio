package com.zeta;

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
        boolean result=authService.logIn("pragnya","BUILDER");
        assertEquals(true,result);
    }




}
