package com.elsy.rynder.io.interactor;

import android.util.Log;

import com.elsy.rynder.RynderApplication;
import com.elsy.rynder.io.ApiConstants;
import com.elsy.rynder.io.callbacks.RestaurantsCallback;
import com.elsy.rynder.io.models.RestaurantsResponse;
import com.elsy.rynder.io.services.RestaurantsApiService;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantsInteractor {

    public RestaurantsApiService apiService;

    public RestaurantsInteractor(RestaurantsApiService apiService) {
        this.apiService = apiService;
    }

    public void getRestaurants(
            final RestaurantsCallback callback,
            final double lat,
            final double lng,
            final double budgetMin,
            final double budgetMax,
            final String token)
    {

        Log.d(RynderApplication.TAG,
                "Getting restaurants from: LAT: " + String.valueOf(lat) + " - LNG " + String.valueOf(lng)
                        + " - MIN: " + String.valueOf(budgetMin) + " - MAX: " + String.valueOf(budgetMax)
                        + " with token " + token);

        Call<RestaurantsResponse> call = apiService.getRestaurants(lat, lng, budgetMin, budgetMax, token);
        Log.d(RynderApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {

                int statusCode = response.code();
                Log.d(RynderApplication.TAG, "ORIGINAL RESTAURANTS RESPONSE RAW: " + response.raw().toString());

                if (response.isSuccessful()){
                    final String newToken = response.headers().get(ApiConstants.HEADER_RESPONSE_TOKEN);
                    Log.d(RynderApplication.TAG, "SUCCESS: " + response.message()+ " with new token " + newToken);
                    RestaurantsResponse restaurantsResponse = response.body();
                    callback.onRestaurantsLoaded(restaurantsResponse.getRestaurants(), newToken);
                } else {
                    callback.onFailedLoad();
                }

            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(RynderApplication.TAG, t.getMessage());
                callback.onFailedLoad();
            }
        });

    }

}
