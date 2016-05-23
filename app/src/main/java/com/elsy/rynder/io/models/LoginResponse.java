package com.elsy.rynder.io.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
