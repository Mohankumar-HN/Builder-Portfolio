package com.zeta.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String password;
    @JsonProperty
    private ROLE_TYPE role;
    public User() {}
    public User(String id, String name, String password, ROLE_TYPE role) {
        this.id = id;
        this.name= name;
        this.password = password;
        this.role = role;
    }

    public String getId(){return id;}
    public String getUserName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ROLE_TYPE getRole() {
        return role;
    }
}
