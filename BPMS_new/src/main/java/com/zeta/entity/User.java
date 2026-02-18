package com.zeta.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String id;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String password;
    @JsonProperty
    private ROLE_TYPE role;
    public User() {}
    public User(String id, String userName, String password, ROLE_TYPE role) {
        this.id = id;
        this.userName= userName;
        this.password = password;
        this.role = role;
    }

    public  String getId(){return id;}
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ROLE_TYPE getRole() {
        return role;
    }
}
