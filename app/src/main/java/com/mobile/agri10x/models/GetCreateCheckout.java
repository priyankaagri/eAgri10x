package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCreateCheckout {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetCreateCheckoutDataa data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetCreateCheckoutDataa getData() {
        return data;
    }

    public void setData(GetCreateCheckoutDataa data) {
        this.data = data;
    }
}
