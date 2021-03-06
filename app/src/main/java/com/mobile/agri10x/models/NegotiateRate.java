package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NegotiateRate {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private NegotiateRateData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NegotiateRateData getData() {
        return data;
    }

    public void setData(NegotiateRateData data) {
        this.data = data;
    }
}
