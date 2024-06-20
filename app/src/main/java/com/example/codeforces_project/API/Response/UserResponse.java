package com.example.codeforces_project.API.Response;

import com.example.codeforces_project.API.ModelResponse.UserAPI;

import java.util.List;

public class UserResponse {
    private String status;
    private List<UserAPI> result;
    private String comment;

    public UserResponse(){

    }

    public UserResponse(String status, List<UserAPI> result, String comment) {
        this.status = status;
        this.result = result;
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserAPI> getResult() {
        return result;
    }

    public void setResult(List<UserAPI> result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
