package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRemoveProduct{

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}