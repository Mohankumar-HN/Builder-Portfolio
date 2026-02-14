package com.zeta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String Name;
    @JsonProperty
    private String password;
    @JsonProperty
    private ROLE_TYPE role;

    public User(String name, String password, ROLE_TYPE role) {
        Name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return password;
    }

    public ROLE_TYPE getRole() {
        return role;
    }
}
