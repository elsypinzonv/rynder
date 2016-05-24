package com.elsy.rynder.io.models;

import com.elsy.rynder.domain.Restaurant;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class RestaurantsResponse {

    @SerializedName("data")
    ArrayList<Restaurant> data;

    public ArrayList<Restaurant> getRestaurants() {
        return data;
    }

}
