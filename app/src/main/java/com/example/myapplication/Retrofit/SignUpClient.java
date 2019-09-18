package com.example.myapplication.Retrofit;

import com.example.myapplication.Models.Registration;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpClient {
    @POST("SignUpMobileAttendence")
    Call<ResponseBody> createAccount(@Body Registration registration);
}
