package com.example.codeforces_project.API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.API.Response.UserResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodeforcesApiService {

    private static final String TAG = "CodeforcesApiService";
    private static final String BASE_URL = "https://codeforces.com/api/";
    private OkHttpClient client;

    public CodeforcesApiService() {
        client = new OkHttpClient();
    }

    public void getUserInfo(String handles, final OnUserResponseListener listener) {
        new UserInfoTask(listener).execute(handles);
    }

    private class UserInfoTask extends AsyncTask<String, Void, String> {

        private OnUserResponseListener listener;

        public UserInfoTask(OnUserResponseListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            String handles = params[0];
            String url = BASE_URL + "user.info?handles=" + handles + "&checkHistoricHandles=true";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    // Handle failure
                    Log.e(TAG, "Failed to get response: " + response.code() + " " + response.message());
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonData) {
            if (jsonData != null) {
                handleUserResponse(jsonData);
            } else {
                Log.e(TAG, "Failed to fetch user info");
                if (listener != null) {
                    listener.onUserResponse(new ArrayList<UserAPI>());
                }
            }
        }

        private void handleUserResponse(String jsonData) {
            try {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(jsonData, UserResponse.class);

                if (userResponse.getStatus().equals("OK")) {
                    List<UserAPI> users = userResponse.getResult();

                    Collections.sort(users, new Comparator<UserAPI>() {
                        @Override
                        public int compare(UserAPI user1, UserAPI user2) {
                            return Integer.compare(user2.getRating(), user1.getRating());
                        }
                    });

                    if (listener != null) {
                        listener.onUserResponse(users);
                    }
                } else {
                    String comment = userResponse.getComment();
                    Log.e(TAG, "Failed to get user info: " + comment);
                    if (listener != null) {
                        listener.onUserResponse(new ArrayList<>());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onUserResponse(new ArrayList<>());
                }
            }
        }
    }

    public interface OnUserResponseListener {
        void onUserResponse(List<UserAPI> users);
    }
}
