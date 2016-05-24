package com.elsy.rynder.modules.maps;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.elsy.rynder.R;
import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.modules.budget.BudgetActivity;
import com.elsy.rynder.modules.login.LoginActivity;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.GPSDataLoader;
import com.elsy.rynder.utils.Injection;
import com.elsy.rynder.utils.preferences_manager.LocationPreferencesManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements MapContract.View, MapContract.Map, OnMapReadyCallback {

    private Toolbar toolbar;
    private MapContract.UserActionsListener mActionsListener;
    private GPSDataLoader gpsDataLoader;
    private ProgressDialog mProgressDialog;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap = null;
    private LocationPreferencesManager locationManager;
    private Marker mMarker;
    private ArrayList<Marker> restaurantsMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initUI();
        initToolbar();
        restaurantsMarks = new ArrayList<>();
        locationManager =Injection.provideLocationPreferencesManager(this);
        gpsDataLoader = new GPSDataLoader(this, locationManager,this);
        mActionsListener = new MapPresenter(
                this,
                Injection.provideRestaurantsInteractor(),
                Injection.provideUserSessionManager(this),
                locationManager,
                Injection.provideBudgetPreferencesManager(this)
        );
        mProgressDialog = ActivityHelper.createModalProgressDialog(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadRestaurants(false);
    }


    @Override
    public void setProgressIndicator(boolean active) {
        if(active){
            mProgressDialog.setMessage("Cargando restaurantes");
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        for (Marker mark: restaurantsMarks){
            mark.remove();
        }
        for(Restaurant restaurant: restaurants){
            markRestaurant(restaurant);
        }

        Toast.makeText(this,"actualizando",Toast.LENGTH_LONG).show();

    }

    @Override
    public void showRestaurantProfileUI(String id, Restaurant restaurant) {

    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.budget:
                ActivityHelper.begin(this, BudgetActivity.class);
                return true;
            case R.id.logout:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doLogout() {
        Injection.provideLocationPreferencesManager(this).clearLocation();
        Injection.provideBudgetPreferencesManager(this).clearBudget();
        Injection.provideUserSessionManager(this).logoutUser();
        ActivityHelper.sendTo(this, LoginActivity.class);
    }

    private void initToolbar(){
       setSupportActionBar(toolbar);
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap =googleMap;
        if(locationManager.hasAlreadyChooseLocation()){
            setupMarker();
        }
    }

    @Override
    public void updateMark(double lat, double lng) {
        LatLng current = new LatLng(lat,lng);
        this.mMarker.setPosition(current);
        zoomToCurrentLatLngPosition(current);
    }

    @Override
    public void updateRestaurantsMarks() {
        mActionsListener.loadRestaurants(false);
    }

    private void markRestaurant(Restaurant restaurant){
        LatLng point = new LatLng(restaurant.getLocationLat(), restaurant.getLocationLng());
       restaurantsMarks.add(mMap.addMarker(
                new MarkerOptions()
                        .position(point)
                        .title(restaurant.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        ));
    }

    private void zoomToCurrentLatLngPosition(LatLng initialPoint) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                initialPoint).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void setupMarker() {
        LatLng initialPoint = new LatLng(locationManager.getLatitude(), locationManager.getLongitude());
        this.mMarker = this.mMap.addMarker(
                new MarkerOptions()
                        .position(initialPoint)
                        .title("Mi ubicacion")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person))
        );
        zoomToCurrentLatLngPosition(initialPoint);
}

}
