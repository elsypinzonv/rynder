package com.elsy.rynder.modules.maps;

import com.elsy.rynder.domain.Restaurant;

import java.util.List;

/**
 * Created by Elsy on 23/05/2016.
 */
public interface MapContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showRestaurants(List<Restaurant> restaurants);

        void showRestaurantProfileUI(String id, Restaurant restaurant);

        void showErrorMessage(String message);

    }
    interface Map {

        void updateMark(double lat, double lng);

        void updateRestaurantsMarks();
    }

    interface UserActionsListener {

        void loadRestaurants(boolean forceUpdate);

        void openRestaurantProfile(Restaurant restaurant);


    }
}
