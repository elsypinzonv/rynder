package com.elsy.rynder.modules.maps;

import android.location.Location;
import android.support.annotation.NonNull;

import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.io.callbacks.RestaurantsCallback;
import com.elsy.rynder.io.interactor.RestaurantsInteractor;
import com.elsy.rynder.utils.GetRestaurantUtil;
import com.elsy.rynder.utils.Injection;
import com.elsy.rynder.utils.preferences_manager.BudgetPreferencesManager;
import com.elsy.rynder.utils.preferences_manager.LocationPreferencesManager;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;
import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class MapPresenter implements MapContract.UserActionsListener, RestaurantsCallback {

    private RestaurantsInteractor mInteractor;
    private MapContract.View mView;
    private UserSessionManager mSessionManager;
    private LocationPreferencesManager mLocationPreferences;
    private BudgetPreferencesManager mBudgetPreferences;
  //  private GPSManager mGPSDataLoader;

    public MapPresenter(
            @NonNull MapContract.View view,
            @NonNull RestaurantsInteractor interactor,
            @NonNull UserSessionManager sessionManager,
            @NonNull LocationPreferencesManager locationPreferences,
            @NonNull BudgetPreferencesManager budgetPreferences
          //  @NonNull GPSManager gpsDataLoader
    ) {
        mInteractor = checkNotNull(interactor);
        mView = checkNotNull(view);
        mSessionManager = checkNotNull(sessionManager);
        mLocationPreferences = checkNotNull(locationPreferences);
        mBudgetPreferences = checkNotNull(budgetPreferences);
       // mGPSDataLoader = gpsDataLoader;
    }

    @Override
    public void loadRestaurants(boolean forceUpdate) {
        mView.setProgressIndicator(true);
        if (forceUpdate) {
           // mInteractor.refreshData();
        }
        mInteractor.getRestaurants(this,
                mLocationPreferences.getLatitude(), mLocationPreferences.getLongitude(),
                mBudgetPreferences.getBudgetMin(), mBudgetPreferences.getBudgetMax(),
                mSessionManager.getTokenSession()
        );
    }

    @Override
    public void openRestaurantProfile(List<Restaurant> restaurants) {
        Restaurant restaurant = GetRestaurantUtil.getRestaurant(mLocationPreferences.getLatitude(),mLocationPreferences.getLongitude());
        mSessionManager.setCurrentRestaurant(restaurant);
        mView.showRestaurantProfileUI(restaurant.getId(),restaurant);
    }


    @Override
    public void onRestaurantsLoaded(ArrayList<Restaurant> restaurants, String newToken) {
        mView.setProgressIndicator(false);
        GetRestaurantUtil.setRestaurants(restaurants);
        mSessionManager.updateSessionToken(newToken);

        if(restaurants != null && !restaurants.isEmpty()){
            if(mSessionManager.isInRestaurant()){
                openRestaurantProfile(restaurants);
            }else{
                mView.showRestaurants(restaurants);
            }
        } else {
            mView.showErrorMessage("No hay restaurantes para mostrar");
        }
    }

    @Override
    public void onFailedLoad() {
        mView.setProgressIndicator(false);
        mView.showErrorMessage("Ocurrió un error");
    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onServerError() {

    }

}
