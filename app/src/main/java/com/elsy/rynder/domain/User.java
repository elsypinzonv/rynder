package com.elsy.rynder.domain;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("auth_token")
    private String tokeSession;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password,String username, String token) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.tokeSession = token;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokeSession() {
        return tokeSession;
    }

    public void setTokeSession(String tokeSession) {
        this.tokeSession = tokeSession;
    }
}
