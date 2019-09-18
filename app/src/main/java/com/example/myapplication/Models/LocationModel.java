package com.example.myapplication.Models;

public class LocationModel {
    double Latitude;
    double Longitude;
    String Address;
    int APIKey;

    public LocationModel(double latitude, double longitude, String address, int APIKey) {
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        this.APIKey = APIKey;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(int APIKey) {
        this.APIKey = APIKey;
    }
}
