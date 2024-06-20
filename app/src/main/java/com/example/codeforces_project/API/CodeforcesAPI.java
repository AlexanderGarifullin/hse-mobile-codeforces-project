package com.example.codeforces_project.API;

import com.example.codeforces_project.API.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CodeforcesAPI {
    @GET("user.info")
    Call<UserResponse> getUserInfo(@Query("handles") String handles, @Query("checkHistoricHandles") boolean checkHistoricHandles);
}
