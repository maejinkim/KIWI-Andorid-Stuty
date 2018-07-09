package com.project.team.teamproject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by 혜진 on 2017-12-02.
 */


public class GpsInfo extends Service implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false;
    // GPS 사용유무
    boolean isNetworkEnabled = false;
    // 네트워크 사용유무
    boolean isGetLocation = false;
    // GPS 상태값

    Location location;
    double lat;
    //위도
    double lon;
    //경도

    private static final long MIN_DISTANCE_UPDATES = 10;
    //gps 정보 업데이트 최소 10미터
    private static final long MIN_TIME_UPDATES = 1000 * 60 * 1;
    //gps 정보 업데이트 시간 1/1000

    protected LocationManager locationManager;

    public GpsInfo(Context context)
    {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation()
    {
        try
        {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled)
            {}else
            {
                this.isGetLocation = true;
                if (isNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_UPDATES,MIN_DISTANCE_UPDATES, this);
                    if(locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null)
                        {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled)
                {
                    if(location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_UPDATES,MIN_DISTANCE_UPDATES, this);
                        if(locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null)
                            {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (SecurityException e)
        {
            e.printStackTrace();
        }
        return  location;
    }
    //GPS 종료하기
    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(GpsInfo.this);
        }
    }
    //get 경도값
    public double getLongitude()
    {
        if(location != null)
        {
            lon = location.getLongitude();
        }
        return lon;
    }
    //get 위도값
    public double getLatitude()
    {
        if(location != null)
        {
            lat = location.getLatitude();
        }
        return lat;
    }
    //GPS or WIFI 유무
    public boolean isGetLocation()
    {
        return this.isGetLocation;
    }

    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS 허가");
        alertDialog.setMessage("GPS 설정을 해야합니다.");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}