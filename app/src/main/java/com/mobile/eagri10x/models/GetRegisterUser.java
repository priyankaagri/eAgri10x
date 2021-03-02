package com.mobile.eagri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRegisterUser {


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("token")
    @Expose
    private String token;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
