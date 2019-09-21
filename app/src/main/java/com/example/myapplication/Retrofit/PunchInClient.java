package com.example.myapplication.Retrofit;

import com.example.myapplication.Models.APIKEY;
import com.example.myapplication.Models.PunchingModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PunchInClient {
    @POST("PunchInTime")
    Call<ResponseBody> punchIn(@Body PunchingModel punchingModel);
}
