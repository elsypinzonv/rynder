package com.elsy.rynder.utils.preferences_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.domain.User;
import com.google.gson.Gson;


public class UserSessionManager {

    private SharedPreferences userPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String USER_PREFERENCES = "userPreferences";
    private static final String IS_USER_LOGIN = "IS_USER_LOGIN";
    private static final String IS_USER_SIGN_UP_RECENTLY = "IS_USER_SIGN_UP_RECENTLY";
    private static final String KEY_SIGN_UP_TOKEN = "KEY_SIGN_UP_TOKEN";

    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN";

    public static final String IS_IN_RESTAURANT = "IS_IN_RESTAURANT";
    public static final String KEY_RESTAURANT = "KEY_RESTAURANT";
    public static final String KEY_RESTAURANT_ID = "KEY_RESTAURANT_ID";

    public UserSessionManager(Context context){
        this.mContext = context;
        userPreferences = mContext.getSharedPreferences(USER_PREFERENCES, PRIVATE_MODE);
        editor = userPreferences.edit();
    }

    public void createUserLoginSession(String name, String token){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.commit();
    }

    public void saveNewSignUpUser(User newUser, String signUpToken){
        editor.putBoolean(IS_USER_SIGN_UP_RECENTLY, true);
        editor.putString(KEY_USERNAME, newUser.getUsername());
        editor.putString(KEY_SIGN_UP_TOKEN, signUpToken);
        editor.commit();
    }

    public String getUsername(){
        return userPreferences.getString(KEY_USERNAME, null);
    }


    public User getCurrentUserLogin(){
        User user = new User();
        user.setUsername(userPreferences.getString(KEY_USERNAME, null));
        user.setTokeSession(userPreferences.getString(KEY_SESSION_TOKEN, null));
        return user;
    }

    public void logoutUser(){
        editor.remove(IS_USER_LOGIN);
        editor.remove(IS_USER_SIGN_UP_RECENTLY);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_SIGN_UP_TOKEN);
        editor.remove(KEY_SESSION_TOKEN);
        //editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return userPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public void saveSignUpToken(String signUpToken) {
        editor.putString(KEY_SIGN_UP_TOKEN, signUpToken);
        editor.commit();
    }

    public void createFbUserLoginSession(User user, String token) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.commit();
    }

    public boolean isRecentlyUserSignUp(){
        return userPreferences.getBoolean(IS_USER_SIGN_UP_RECENTLY, false);
    }

    public void updateSessionToken(String sessionToken) {
        if(sessionToken != null){
            editor.putString(KEY_SESSION_TOKEN, sessionToken);
            editor.commit();
        }
    }

    public User getLastSignUpUser() {
        User user = new User();
        user.setUsername(userPreferences.getString(KEY_USERNAME, null));
        user.setTokeSession(userPreferences.getString(KEY_SIGN_UP_TOKEN, null));
        return user;
    }

    public String getTokenSession() {
        return userPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    public boolean isInRestaurant(){
        return userPreferences.getBoolean(IS_IN_RESTAURANT, false);
    }

    public void dropCurrentRestaurant(){
        editor.remove(IS_IN_RESTAURANT);
        editor.remove(KEY_RESTAURANT);
        editor.remove(KEY_RESTAURANT_ID);
        editor.commit();
    }

    public void setCurrentRestaurant(Restaurant restaurant){
        editor.putBoolean(IS_IN_RESTAURANT, true);
        editor.putString(KEY_RESTAURANT, new Gson().toJson(restaurant));
        editor.putString(KEY_RESTAURANT_ID, restaurant.getId());
        editor.commit();
    }

    public Restaurant getCurrentRestaurant(){
        String restaurantJSON = userPreferences.getString(KEY_RESTAURANT, null);
        return new Gson().fromJson(restaurantJSON, Restaurant.class);
    }

    public void setIsInRestaurant() {
        editor.putBoolean(IS_IN_RESTAURANT, true);
        editor.commit();
    }
}