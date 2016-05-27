package com.elsy.rynder.utils;

import android.location.Location;

import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.modules.maps.MapContract;

import java.util.List;

/**
 * Created by Elsy on 25/05/2016.
 */
public class GetRestaurantUtil {

    private static List<Restaurant> mRestaurantList;

    public static void setRestaurants(List<Restaurant> restaurantsList){
        mRestaurantList = restaurantsList;
    }

    public static Restaurant getRestaurant(double latitud, double longitud){
        Restaurant rest;
        rest = mRestaurantList.get(0);
        float minDistance = getDistanceBetweenToPoints(latitud,longitud, rest.getLocationLat(), rest.getLocationLng());
        float actualDistance;
        for(Restaurant restaurant : mRestaurantList){
            actualDistance=getDistanceBetweenToPoints(latitud,longitud,restaurant.getLocationLat(), restaurant.getLocationLng());
            if(actualDistance<minDistance){
                rest=restaurant;
                minDistance=actualDistance;
            }
        }
        return  rest;
    }

    public static Restaurant getRestaurant(List<Restaurant> restaurantList, double latitud, double longitud){
        Restaurant rest;
        rest = restaurantList.get(0);
        float minDistance = getDistanceBetweenToPoints(latitud,longitud, rest.getLocationLat(), rest.getLocationLng());
        float actualDistance;
        for(Restaurant restaurant : restaurantList){
            actualDistance=getDistanceBetweenToPoints(latitud,longitud,restaurant.getLocationLat(), restaurant.getLocationLng());
            if(actualDistance<minDistance){
                rest=restaurant;
                minDistance=actualDistance;
            }
        }
        return  rest;
    }

    public static float getDistanceBetweenToPoints(double latitud, double longitud, double lat, double lng){
        float[] results = new float[1];
        android.location.Location.distanceBetween(
                latitud,
                longitud,
                lat,
                lng,
                results
        );
        float currentDistance = results[0];
        return currentDistance;
    }

    public static List<Restaurant> getRestaurants() {
        return mRestaurantList;
    }
}
