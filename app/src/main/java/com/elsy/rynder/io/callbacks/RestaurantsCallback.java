package com.elsy.rynder.io.callbacks;


import com.elsy.rynder.domain.Restaurant;
import java.util.ArrayList;

public interface RestaurantsCallback extends ServerCallback{

    void onRestaurantsLoaded(ArrayList<Restaurant> restaurants, String newToken);

    void onFailedLoad();

}
