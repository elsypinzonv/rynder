package com.elsy.rynder.modules.restaurant_profile;

import android.support.annotation.NonNull;
import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.io.callbacks.RestaurantCallback;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class RestaurantProfilePresenter implements RestaurantProfileContract.UserActionsListener, RestaurantCallback {

    private RestaurantProfileContract.View mView;
    private RestaurantInteractor mInteractor;
    private UserSessionManager mSessionManager;

    public RestaurantProfilePresenter(
            @NonNull RestaurantProfileContract.View view,
            @NonNull RestaurantInteractor interactor,
            @NonNull UserSessionManager sessionManager
    ) {
        mView = checkNotNull(view);
        mInteractor = checkNotNull(interactor);
        mSessionManager = checkNotNull(sessionManager);
    }

    @Override
    public void loadRestaurantInformation(String restaurantID) {
        mView.setProgressIndicator(true);
        mInteractor.getDetails(this, restaurantID, mSessionManager.getTokenSession());
    }

    @Override
    public void onDetailsSuccess(Restaurant restaurant, String newToken) {
        mView.setProgressIndicator(false);
        mSessionManager.updateSessionToken(newToken);
        mView.showRestaurant(restaurant);
    }

    @Override
    public void onFailedGetDetail() {
        mView.setProgressIndicator(false);
        mView.showFailedLoadMessage();
    }


}
