package com.elsy.rynder.utils;

import android.content.Context;

import com.elsy.rynder.io.interactor.LoginInteractor;
import com.elsy.rynder.io.interactor.RestaurantInteractor;
import com.elsy.rynder.io.interactor.RestaurantsInteractor;
import com.elsy.rynder.io.services.LoginApiService;
import com.elsy.rynder.io.services.RestaurantApiService;
import com.elsy.rynder.io.services.RestaurantsApiService;
import com.elsy.rynder.utils.preferences_manager.BudgetPreferencesManager;
import com.elsy.rynder.utils.preferences_manager.LocationPreferencesManager;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;


public class Injection {


    public static LoginInteractor provideLoginInteractor(){
        return new LoginInteractor(provideLoginApiService());
    }

    private static LoginApiService provideLoginApiService() {
        return ServiceGenerator.createService(LoginApiService.class);
    }

    public static RestaurantsInteractor provideRestaurantsInteractor() {
        return new RestaurantsInteractor(provideRestaurantsApiService());
    }

    public static RestaurantInteractor provideRestaurantInteractor() {
        return new RestaurantInteractor(provideRestaurantApiService());
    }

    private static RestaurantApiService provideRestaurantApiService() {
        return ServiceGenerator.createService(RestaurantApiService.class);
    }

    private static RestaurantsApiService provideRestaurantsApiService() {
        return ServiceGenerator.createService(RestaurantsApiService.class);
    }

    public static UserSessionManager provideUserSessionManager(Context context) {
        return new UserSessionManager(context);
    }

    public static LocationPreferencesManager provideLocationPreferencesManager(Context context) {
        return new LocationPreferencesManager(context);
    }


    public static BudgetPreferencesManager provideBudgetPreferencesManager(Context context) {
        return new BudgetPreferencesManager(context);
    }
}
