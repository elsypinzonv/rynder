package com.elsy.rynder.io.interactor;

import com.elsy.rynder.io.ApiConstants;
import com.elsy.rynder.io.callbacks.RestaurantCallback;
import com.elsy.rynder.io.models.RestaurantResponse;
import com.elsy.rynder.io.services.RestaurantApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantInteractor {

    public RestaurantApiService apiService;

    public RestaurantInteractor(RestaurantApiService apiService) {
        this.apiService = apiService;
    }

    public void getDetails(final RestaurantCallback callback, final String restaurantID, final String token){

        Call<RestaurantResponse> call = apiService.getRestaurantDetail(restaurantID, token);

        call.enqueue(new Callback<RestaurantResponse>() {

            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful()){
                    final String newToken = response.headers().get(ApiConstants.HEADER_RESPONSE_TOKEN);

                    RestaurantResponse restaurantResponse = response.body();
                    callback.onDetailsSuccess(restaurantResponse.getDetails(), newToken);

                } else {
                    callback.onFailedGetDetail();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedGetDetail();
            }
        });
    }


}
