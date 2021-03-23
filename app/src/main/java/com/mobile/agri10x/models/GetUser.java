package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUser{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetUserData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetUserData getData() {
        return data;
    }

    public void setData(GetUserData data) {
        this.data = data;
    }

}
