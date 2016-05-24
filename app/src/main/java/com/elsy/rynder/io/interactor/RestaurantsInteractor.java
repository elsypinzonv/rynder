package com.elsy.rynder.io.interactor;

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

        Call<RestaurantsResponse> call = apiService.getRestaurants(lat, lng, budgetMin, budgetMax, token);

        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {

                int statusCode = response.code();

                if (response.isSuccessful()){
                    final String newToken = response.headers().get(ApiConstants.HEADER_RESPONSE_TOKEN);
                    RestaurantsResponse restaurantsResponse = response.body();
                    callback.onRestaurantsLoaded(restaurantsResponse.getRestaurants(), newToken);
                } else {
                    callback.onFailedLoad();
                }

            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedLoad();
            }
        });

    }

}
