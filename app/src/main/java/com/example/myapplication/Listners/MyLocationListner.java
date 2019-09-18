package com.example.myapplication.Listners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListner implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

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
