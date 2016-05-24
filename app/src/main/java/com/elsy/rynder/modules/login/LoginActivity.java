package com.elsy.rynder.modules.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.elsy.rynder.R;
import com.elsy.rynder.modules.maps.MapActivity;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.Injection;
import com.elsy.rynder.utils.TextViewUtils;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private Button btn_login;
    private EditText edit_username;
    private TextView tx_app_name;
    private ProgressDialog mProgressDialog;


    private LoginContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_login);
        initUI();
        TextViewUtils.setLobsterTypeface(this, tx_app_name);

        mActionsListener = new LoginPresenter(this,
                Injection.provideLoginInteractor(),
                Injection.provideUserSessionManager(getApplicationContext()),
                Injection.provideLocationPreferencesManager(getApplicationContext()),
                Injection.provideBudgetPreferencesManager(getApplicationContext())
        );

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                mActionsListener.doLogin(
                        edit_username.getText().toString().trim()
                );
            }
        });

        setupProgressDialog();
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
       ActivityHelper.sendTo(this, MapActivity.class);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active){
            mProgressDialog.show();
            btn_login.setText(getString(R.string.lbl_loading_message));
        }else {
            mProgressDialog.dismiss();
            btn_login.setText(getString(R.string.lbl_btn_login));
        }
    }


    @Override
    public void showEmptyDataMessage() {
        Snackbar.make(edit_username, getString(R.string.empty_data_message), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showLoginFailedMessage(String message) {
        Snackbar.make(edit_username, message, Snackbar.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage(getString(R.string.lbl_login_sesion));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
    }

    private void initUI(){
        tx_app_name = (TextView) findViewById(R.id.app_name);
        btn_login = (Button) findViewById(R.id.login);
        edit_username = (EditText) findViewById(R.id.username);
    }
}
