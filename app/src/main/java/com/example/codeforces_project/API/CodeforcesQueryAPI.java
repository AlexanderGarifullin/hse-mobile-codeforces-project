package com.example.codeforces_project.API;

import android.util.Log;

import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.API.Response.UserResponse;

import java.util.List;


import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeforcesQueryAPI {

    public interface UserInfoCallback {
        void onSuccess(List<UserAPI> users);
        void onError(String errorMessage);
    }

    public void fetchUserInfo(String handle, UserInfoCallback callback) {

        CodeforcesAPI api = ApiClient.getClient().create(CodeforcesAPI.class);

        HttpUrl url = api.getUserInfoUrl(handle, true); // Получение URL запроса

        Log.i("api", "Request URL: " + url.toString()); // Вывод URL в Logcat

        Call<UserResponse> call = api.getUserInfo(handle, true);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("api", "good");
                    List<UserAPI> users = response.body().getResult();
                    callback.onSuccess(users);
                } else {
                    Log.i("api", "onResponse failure");
                    callback.onError("Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i("api", "error failure");
                Log.i("api",  t.getMessage());
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}
