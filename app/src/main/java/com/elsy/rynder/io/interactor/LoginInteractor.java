package com.elsy.rynder.io.interactor;

import android.widget.Toast;

import com.elsy.rynder.domain.User;
import com.elsy.rynder.io.callbacks.LoginCallback;
import com.elsy.rynder.io.models.LoginResponse;
import com.elsy.rynder.io.services.LoginApiService;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor {

    public LoginApiService apiService;

    public LoginInteractor(LoginApiService apiService) {
        this.apiService = apiService;
    }

    public void doLogin(final LoginCallback callback, final String email, final String password, final String userName){

        final User user = new User(email, password);
        Call<LoginResponse> call = apiService.loginResult(user);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                int statusCode = response.code();

                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    String tokenSession = response.headers().get("token");
                    if(tokenSession != null){
                        callback.onLoginSuccess(new User(email,password,userName,tokenSession));
                    }else{
                        if(response.message().equals("Wrong credentials!")){
                            callback.onWrongCredentials();
                        } else {
                            callback.onFailedLogin("Ha ocurrido un error");
                        }
                    }

                } else {
                    try {
                        String messageError = response.errorBody().string();
                        callback.onFailedLogin("Ups! Verifica tus datos");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //TODO: Change to better implementation
                //callback.onFailedLogin(t.getMessage());
                if(t.getMessage().equals("Token error!")){
                    callback.onFailedLogin(t.getMessage());
                } else if (t.getMessage().equals("Wrong credentials!")) {
                    callback.onWrongCredentials();
                } else {
                    callback.onNetworkError();
                }
            }
        });
    }

}
