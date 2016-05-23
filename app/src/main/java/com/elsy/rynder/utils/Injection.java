package com.elsy.rynder.utils;

import android.content.Context;

import com.elsy.rynder.io.interactor.LoginInteractor;
import com.elsy.rynder.io.services.LoginApiService;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;


public class Injection {


    public static LoginInteractor provideLoginInteractor(){
        return new LoginInteractor(provideLoginApiService());
    }

    private static LoginApiService provideLoginApiService() {
        return ServiceGenerator.createService(LoginApiService.class);
    }

    public static UserSessionManager provideUserSessionManager(Context context) {
        return new UserSessionManager(context);
    }

}
