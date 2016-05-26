package com.elsy.rynder.domain;

import com.google.gson.annotations.SerializedName;


public class RestaurantPhone {

    @SerializedName("number")
    private String number;

    @SerializedName("description")
    private String description;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
