package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCreateCheckoutDetails {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetCreateCheckoutData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetCreateCheckoutData getData() {
        return data;
    }

    public void setData(GetCreateCheckoutData data) {
        this.data = data;
    }
}
