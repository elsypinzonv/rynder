package com.elsy.rynder.modules.login;


import com.elsy.rynder.domain.User;
import com.elsy.rynder.io.callbacks.LoginCallback;
import com.elsy.rynder.io.interactor.LoginInteractor;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;

public class LoginPresenter implements LoginContract.UserActionsListener, LoginCallback {

    private LoginContract.View mLoginView;
    private UserSessionManager mSessionManager;
    private LoginInteractor mInteractor;

    private String tempEmail;
    private String tempPassword;

    public LoginPresenter(
            LoginContract.View mLoginView,
            LoginInteractor interactor,
            UserSessionManager sessionManager
    ) {
        this.mLoginView = mLoginView;
        mInteractor = interactor;
        mSessionManager = sessionManager;
    }

    @Override
    public void doLogin(final String username) {
        validate(username);
    }

    private void validate(final String username){
        if(username.trim().toString().isEmpty()){
            mLoginView.showEmptyDataMessage();
        }else{
            onValidationSucceeded(username);
        }

    }

    public void onValidationSucceeded(String username) {
        tempEmail = "prueba@prueba.com";
        tempPassword = "prueba";
        tempEmail = tempEmail.trim().toString();
        tempPassword = tempPassword.trim().toString();
        mLoginView.setProgressIndicator(true);
        mInteractor.doLogin(this, tempEmail, tempPassword,username);
    }


    @Override
    public void onLoginSuccess(User user) {
        mLoginView.setProgressIndicator(false);
        mSessionManager.createUserLoginSession(user.getUsername(), user.getTokeSession());
        mLoginView.onLoginResult(true, 1);
    }

    @Override
    public void onWrongCredentials() {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage(EMAIL_ERROR);

    }

    @Override
    public void onFailedLogin(String message) {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage(message);
    }

    @Override
    public void onNetworkError() {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage(NETWORK_ERROR);
    }

    @Override
    public void onServerError() {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage(SERVER_ERROR);
    }

    private final String NETWORK_ERROR = "Network error";
    private final String SERVER_ERROR = "Server error";
    private final String EMAIL_ERROR = "Correo o contraseña incorrecta";
}