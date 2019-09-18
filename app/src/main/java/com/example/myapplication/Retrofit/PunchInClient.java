package com.example.myapplication.Retrofit;

import com.example.myapplication.Models.APIKEY;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PunchInClient {
    @POST("GetLatLong")
    Call<ResponseBody> getLocationList(@Body APIKEY apikey);
}
