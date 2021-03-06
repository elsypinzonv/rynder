package com.elsy.rynder.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.elsy.rynder.modules.maps.MapContract;
import com.elsy.rynder.utils.preferences_manager.LocationPreferencesManager;


public class GPSManager implements LocationListener {

    private Context mContext;
    private LocationPreferencesManager mLocationManager;
    private android.location.Location mExpectedLocation;
    private LocationManager mManager;
    private MapContract.Location mActionListener;
    private boolean firstLocation;

    protected final int APP_USABLE_RADIUS = 1000;

    public GPSManager(Context context, LocationPreferencesManager manager, MapContract.Location view) {
        mContext = context;
        mLocationManager = manager;
        mActionListener = view;
        firstLocation= true;
        this.mExpectedLocation = getExpectedLocation();
        this.mManager = getManager();

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        mActionListener.updateMark(lat,lng);
      float[] results = new float[1];
        android.location.Location.distanceBetween(
                mExpectedLocation.getLatitude(),
                mExpectedLocation.getLongitude(),
                lat,
                lng,
                results
        );
        float currentDistance = results[0];
        onLocationUpdated(location, currentDistance);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

   protected android.location.Location getExpectedLocation() {
        android.location.Location expected = new android.location.Location("");
        expected.setLatitude(mLocationManager.getLatitude());
        expected.setLongitude(mLocationManager.getLongitude());

        return expected;
    }

    private LocationManager getManager() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (locationPermissionsAvailable()) {
            final long MIN_TIME_BETWEEN_UPDATES = 1000;
            final float MIN_DISTANCE_CHANGE_BETWEEN_UPDATES = 1;

            Criteria criteria = new Criteria();
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);

            validateLocationSettings();
            locationManager.requestLocationUpdates(
                    locationManager.getBestProvider(criteria, true),
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_CHANGE_BETWEEN_UPDATES,
                    this);
        } else {

        }
        return locationManager;
    }

    private void validateLocationSettings(){
        if( ! isLocationEnabled(mContext)){

            final Context current = mContext;
            AlertDialog.Builder dialog = new AlertDialog.Builder( current );
            dialog.setMessage("Parece que la localizacion no esta activada, ¿Desea activar la localizacion?");
            dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    current.startActivity( myIntent );
                }
            });
            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                   //TODO: MORIRA, PONER QUE SE DESLOGUE
                }
            });
            dialog.show();
        }
    }

    private boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private boolean locationPermissionsAvailable(){
        return
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED;
    }

   protected void onLocationUpdated(android.location.Location currentLocation, float currentDistance){
        if( currentDistance > APP_USABLE_RADIUS){
            Toast.makeText(mContext, currentDistance+" Se alejó lo suficiente para actualizar Restaurantes",Toast.LENGTH_LONG).show();
            mLocationManager.registerLocationValues(currentLocation.getLatitude(), currentLocation.getLongitude());
            mExpectedLocation = currentLocation;
            mActionListener.updateRestaurantsMarks();
        }
    }

}
