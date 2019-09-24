package com.example.myapplication.Models;

public class PunchingModel {
    int apikey;
    String type;
    int LocID;
    double distance;

    public PunchingModel(int apikey, String type, int locID, double distance) {
        this.apikey = apikey;
        this.type = type;
        LocID = locID;
        this.distance = distance;
    }

    public int getApikey() {
        return apikey;
    }

    public void setApikey(int apikey) {
        this.apikey = apikey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLocID() {
        return LocID;
    }

    public void setLocID(int locID) {
        LocID = locID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
