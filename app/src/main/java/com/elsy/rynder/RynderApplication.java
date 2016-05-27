package com.elsy.rynder;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.modules.maps.MapActivity;
import com.elsy.rynder.modules.restaurant_profile.RestaurantProfile;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.GetRestaurantUtil;
import com.elsy.rynder.utils.Injection;
import com.elsy.rynder.utils.preferences_manager.LocationPreferencesManager;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

/**
 * Created by luisburgos on 26/05/16.
 */
public class RynderApplication extends Application {

    public static final String TAG = "RynderApplication";
    public static final String CLASSROOM_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    public static final Integer BEACON_MAJOR = 63463;
    public static final Integer BEACON_MINOR = 21120;

    private BeaconManager beaconManager;
    private LocationPreferencesManager mLocationPreferences;
    //private ContentPreferencesManager mContentPreferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationPreferences = Injection.provideLocationPreferencesManager(getApplicationContext());
        //mContentPreferencesManager = Injection.provideContentPreferencesManager(getApplicationContext());
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "Monitoreando Region");
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString(CLASSROOM_BEACON_UUID),
                        BEACON_MAJOR,
                        BEACON_MINOR)
                );
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.d(TAG, "Entrando a Region");

                Injection
                        .provideUserSessionManager(getApplicationContext())
                        .setIsInRestaurant();

                Restaurant restaurant = null;

                if(GetRestaurantUtil.getRestaurants() != null) {
                    restaurant = GetRestaurantUtil.getRestaurant(
                            mLocationPreferences.getLatitude(),
                            mLocationPreferences.getLongitude()
                    );
                }

                if(restaurant != null) {
                    Injection
                            .provideUserSessionManager(getApplicationContext())
                            .setCurrentRestaurant(restaurant);

                    sendToProfile();
                    showNotification("Bienvenido!", "Ahora puedes visualizar el perfil del restaurante");
                }

            }
            @Override
            public void onExitedRegion(Region region) {
                Log.d(TAG, "Saliendo de Region");
                Injection
                        .provideUserSessionManager(getApplicationContext())
                        .dropCurrentRestaurant();
                sendToMap();
                showNotification("Adiós","Hasta luego, regresa pronto");
            }


        });
    }

    private void sendToMap() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sendToProfile() {
        Intent intent = new Intent(this, RestaurantProfile.class);
        //intent.putExtra("restaurantID", restaurant.getId());
        //intent.putExtra("restaurant", new Gson().toJson(restaurant));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, SplashActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

}
