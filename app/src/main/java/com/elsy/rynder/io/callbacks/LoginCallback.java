package com.elsy.rynder.io.callbacks;

import com.elsy.rynder.domain.User;

public interface LoginCallback extends ServerCallback {

    void onLoginSuccess(User user);
    void onWrongCredentials();
    void onFailedLogin(String message);

}
