package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCart {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UpdateCartData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UpdateCartData getData() {
        return data;
    }

    public void setData(UpdateCartData data) {
        this.data = data;
    }

}