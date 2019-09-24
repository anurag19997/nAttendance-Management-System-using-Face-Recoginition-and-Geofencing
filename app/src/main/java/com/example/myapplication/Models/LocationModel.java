package com.example.myapplication.Models;

public class LocationModel {
    String type;
    int APIKey;
    int CityId;
    String CityName;
    int LocId;
    String DPName;
    String DCName;
    String LOC;
    double Latitude;
    double Longitude;

    public LocationModel() {
    }

    public LocationModel(String type, int APIKey, int cityId, String cityName, int locId, String DPName, String DCName, String LOC, double latitude, double longitude) {
        this.type = type;
        this.APIKey = APIKey;
        CityId = cityId;
        CityName = cityName;
        LocId = locId;
        this.DPName = DPName;
        this.DCName = DCName;
        this.LOC = LOC;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(int APIKey) {
        this.APIKey = APIKey;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public int getLocId() {
        return LocId;
    }

    public void setLocId(int locId) {
        LocId = locId;
    }

    public String getDPName() {
        return DPName;
    }

    public void setDPName(String DPName) {
        this.DPName = DPName;
    }

    public String getDCName() {
        return DCName;
    }

    public void setDCName(String DCName) {
        this.DCName = DCName;
    }

    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
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

    //    public LocationModel(double latitude, double longitude, String address, int APIKey) {
//        Latitude = latitude;
//        Longitude = longitude;
//        Address = address;
//        this.APIKey = APIKey;
//    }
//
//    public double getLatitude() {
//        return Latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        Latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return Longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        Longitude = longitude;
//    }
//
//    public String getAddress() {
//        return Address;
//    }
//
//    public void setAddress(String address) {
//        Address = address;
//    }
//
//    public int getAPIKey() {
//        return APIKey;
//    }
//
//    public void setAPIKey(int APIKey) {
//        this.APIKey = APIKey;
//    }
}
