package com.elsy.rynder.io.services;

import com.elsy.rynder.io.ApiConstants;
import com.elsy.rynder.io.models.RestaurantsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface RestaurantsApiService {

    @GET(ApiConstants.RESTAURANTS_LEVEL_2_URL)
    Call<RestaurantsResponse> getRestaurants(
            @Path(ApiConstants.KEY_LAT_PATH) double lat,
            @Path(ApiConstants.KEY_LNG_PATH) double lng,
            @Path(ApiConstants.KEY_BUDGET_MIN_PATH) double budgetMin,
            @Path(ApiConstants.KEY_BUDGET_MAX_PATH) double budgetMax,
            @Header(ApiConstants.HEADER_REQUEST_TOKEN) String token
    );

}
