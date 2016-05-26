package com.elsy.rynder.modules.menu;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

public class MenuPresenter implements SensorListener, MenuContract.UserActionsListener {

    private SensorManager sensorMgr;
    private long lastUpdate = -1;
    private float x;
    private float last_x;
    private Context mContext;
    private MenuContract.View mActionListener;

    public MenuPresenter(Context context, MenuContract.View view) {
        this.mContext = context;
        this.mActionListener = view;
        configSensor();

    }

    public void unRegisterListener(){
        if (sensorMgr != null) {
            sensorMgr.unregisterListener(this,
                    SensorManager.SENSOR_ACCELEROMETER);
            sensorMgr = null;
        }
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 100) {
            lastUpdate = curTime;

            x = values[SensorManager.DATA_X];


            if(Round(x,4)>10.0000){
                mActionListener.changeRightPage();
            }
            else if(Round(x,4)<-10.0000){
                mActionListener.changeLeftPage();
            }

            last_x = x;

        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

    private void configSensor(){
        sensorMgr = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        boolean accelSupported = sensorMgr.registerListener(this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

        if (!accelSupported) {
            sensorMgr.unregisterListener(this,
                    SensorManager.SENSOR_ACCELEROMETER);
        }
    }

    private static float Round(float Rval, int Rpl) {
        float p = (float)Math.pow(10,Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float)tmp/p;
    }


}
