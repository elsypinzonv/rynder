package com.elsy.rynder.io.services;


import com.elsy.rynder.domain.User;
import com.elsy.rynder.io.ApiConstants;
import com.elsy.rynder.io.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST(ApiConstants.LOGIN_URL)
    Call<LoginResponse> loginResult(@Body User user);

}
