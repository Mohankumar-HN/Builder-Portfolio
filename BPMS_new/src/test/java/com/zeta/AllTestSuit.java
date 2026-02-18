package com.zeta;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        TestProjectService.class,
        TestAuthService.class
})

public class AllTestSuit {
}