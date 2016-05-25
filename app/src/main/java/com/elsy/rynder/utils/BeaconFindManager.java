package com.elsy.rynder.utils;

import android.bluetooth.BluetoothAdapter;

import com.elsy.rynder.modules.maps.MapContract;
import com.estimote.sdk.Beacon;
import android.content.Context;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import java.util.List;
import java.util.UUID;

public class BeaconFindManager implements BeaconManager.MonitoringListener {

    private BeaconManager mManager;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean inBeaconZone = false;
    private Context mContext;
    private MapContract.Beacon actionListener;


    public BeaconFindManager(Context context, MapContract.Beacon view) {
        this.mContext = context;
        this.actionListener = view;
        this.mManager = getBeaconManager();
        initBluetooth();
    }

    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
        inBeaconZone = true;
        actionListener.updateView();
    }

    @Override
    public void onExitedRegion(Region region) {
        inBeaconZone = false;
    }

    private void initBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            actionListener.notifyDontHaveBluetooth();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            actionListener.activeBluetooth();
        }
    }

    private BeaconManager getBeaconManager() {

        final BeaconManager beaconManager = new BeaconManager(mContext);

        beaconManager.setMonitoringListener(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("C8AB1EA5-6A81-475E-9AAF-8DAB38E8E7AA"),
                        63463, 21120));
            }
        });
        return beaconManager;
    }


}
