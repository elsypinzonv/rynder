package com.elsy.rynder.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class ActivityHelper {

    public static void begin(Activity activity, Class classTo) {
        Intent intent = new Intent().setClass(activity, classTo);
        activity.startActivity(intent);
    }

    public static void sendTo(Activity activity, Class classTo) {
        Intent intent = new Intent().setClass(activity, classTo);
        activity.startActivity(intent);
        activity.finish();
    }

    public static ProgressDialog createModalProgressDialog(Activity activity) {
        return createModalProgressDialog(activity, null);
    }

    public static ProgressDialog createModalProgressDialog(Activity activity, String dialogMessage) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if(dialogMessage != null){
            progressDialog.setMessage(dialogMessage);
        }
        return progressDialog;
    }
}
