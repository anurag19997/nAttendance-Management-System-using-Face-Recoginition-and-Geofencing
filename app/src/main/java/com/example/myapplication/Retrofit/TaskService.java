package com.example.myapplication.Retrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TaskService {
    @POST("GetMyTaskListJson")
    Call<ResponseBody> getAllTasks(@Body RequestBody userLoginId);
}
