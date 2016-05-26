package com.elsy.rynder.io.callbacks;

import com.elsy.rynder.domain.Restaurant;

public interface RestaurantCallback {

    void onDetailsSuccess(Restaurant restaurant, String newToken);

    void onFailedGetDetail();

}
