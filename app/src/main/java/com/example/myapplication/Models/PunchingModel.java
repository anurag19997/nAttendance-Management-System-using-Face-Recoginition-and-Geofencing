package com.example.myapplication.Models;

public class PunchingModel {
    int id;
    double distance;

    public PunchingModel(int apikey, double distance) {
        this.id = apikey;
        this.distance = distance;
    }

    public int getPunchingModelId() {
        return id;
    }

    public void setPunchingModelId(int apikey) {
        this.id = apikey;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
