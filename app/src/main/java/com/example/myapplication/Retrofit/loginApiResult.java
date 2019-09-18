package com.example.myapplication.Retrofit;

public class loginApiResult {
    private String userId;
    private String password;

    public loginApiResult() {
    }

    public loginApiResult(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
