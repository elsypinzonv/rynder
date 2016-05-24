package com.elsy.rynder.utils.preferences_manager;

import android.content.Context;
import android.content.SharedPreferences;


public class LocationPreferencesManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String LOCATION_PREFERENCES = "LOCATION_PREFERENCES";

    public static final String KEY_IS_LOCATION_SET = "KEY_IS_LOCATION_SET";
    public static final String KEY_LAT = "KEY_LAT";
    public static final String KEY_LNG = "KEY_LNG";

    public LocationPreferencesManager(Context context){
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(LOCATION_PREFERENCES, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void registerLocationValues(double lat, double lng){
        registerPreferences(KEY_IS_LOCATION_SET, true);
        registerPreferences(KEY_LAT, lat);
        registerPreferences(KEY_LNG, lng);
    }

    public void registerPreferences(String preference, boolean value){
        mEditor.putBoolean(preference, true);
        mEditor.commit();
    }

    public void registerPreferences(String preference, double value){
        mEditor.putLong(preference, Double.doubleToLongBits(value));
        mEditor.commit();
    }

    public double getLatitude(){
        return Double.longBitsToDouble(mPreferences.getLong(KEY_LAT, 0));
    }

    public double getLongitude(){
        return Double.longBitsToDouble(mPreferences.getLong(KEY_LNG, 0));
    }

    public boolean hasAlreadyChooseLocation(){
        return mPreferences.getBoolean(KEY_IS_LOCATION_SET, false);
    }

    public void clearLocation(){
        mEditor.clear();
        mEditor.commit();
    }

}
