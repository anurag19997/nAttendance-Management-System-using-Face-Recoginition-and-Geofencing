package com.example.myapplication.Models;

public class UserLogin {
    String Username;
    String Password;
//    int ApiKey;

    public UserLogin(String username, String password) {
        Username = username;
        Password = password;
    }


//    public UserLogin(String username, String password, int ApiKey) {
//        Username = username;
//        Password = password;
////        this.ApiKey = ApiKey;
//    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

//    public int getApiKey() {
//        return ApiKey;
//    }
//
//    public void setApiKey(int apiKey) {
//        ApiKey = apiKey;
//    }
}
