package com.example.myapplication.Models;

public class User {

    public String no;
    public String lat;
    public String longitude;
    public String lastDayIn;
    public String lastDayOut;
    public String emailId;
    public String name;


    public User(String no, String lat, String longitude, String lastDayIn, String lastDayOut, String emailId, String name) {
        this.no = no;
        this.lat = lat;
        this.longitude = longitude;
        this.lastDayIn = lastDayIn;
        this.lastDayOut = lastDayOut;
        this.emailId = emailId;
        this.name = name;
    }




    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String no, String lat, String longitude, String lastDayIn, String lastDayOut) {
        this.no = no;
        this.lat = lat;
        this.longitude = longitude;
        this.lastDayIn = lastDayIn;
        this.lastDayOut = lastDayOut;
    }

    public User(String no, String lastDayIn, String lastDayOut) {
        this.no = no;
        this.lastDayIn = lastDayIn;
        this.lastDayOut = lastDayOut;
    }

    public User() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLastDayIn() {
        return lastDayIn;
    }

    public void setLastDayIn(String lastDayIn) {
        this.lastDayIn = lastDayIn;
    }

    public String getLastDayOut() {
        return lastDayOut;
    }

    public void setLastDayOut(String lastDayOut) {
        this.lastDayOut = lastDayOut;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }
}
