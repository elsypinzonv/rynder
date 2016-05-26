package com.elsy.rynder.modules.restaurant_profile;


import com.elsy.rynder.domain.Restaurant;

/**
 * Created by luisburgos on 25/04/16.
 */
public interface RestaurantProfileContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showRestaurant(Restaurant restaurant);

        void showFailedLoadMessage();

        void showErrorMessage(String message);
    }

    interface UserActionsListener {

        void loadRestaurantInformation(String restaurantID);

    }

}
