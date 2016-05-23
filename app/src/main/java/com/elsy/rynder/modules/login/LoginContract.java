package com.elsy.rynder.modules.login;


import java.util.List;

public interface LoginContract {

    interface View {

        void onLoginResult(Boolean result, int code);

        void setProgressIndicator(boolean active);

        void showEmptyDataMessage();

        void showLoginFailedMessage(String message);

    }

    interface UserActionsListener {

        void doLogin(String username);

    }


}
