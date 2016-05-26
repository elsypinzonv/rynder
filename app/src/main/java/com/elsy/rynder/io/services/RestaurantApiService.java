package com.elsy.rynder.io.services;

import com.elsy.rynder.io.ApiConstants;
import com.elsy.rynder.io.models.RestaurantResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RestaurantApiService {

    @GET(ApiConstants.RESTAURANT_DETAIL_URL)
    Call<RestaurantResponse> getRestaurantDetail(
            @Path(ApiConstants.KEY_RESTAURANT_ID_PATH) String restaurantID,
            @Header(ApiConstants.HEADER_REQUEST_TOKEN) String token
    );



}