package com.elsy.rynder.io.models;

import com.elsy.rynder.domain.Restaurant;
import com.google.gson.annotations.SerializedName;

public class RestaurantResponse {

    @SerializedName("data")
    Restaurant data;

    public Restaurant getDetails() {
        return data;
    }

}
