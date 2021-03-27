package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderList {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetOrderListData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetOrderListData getData() {
        return data;
    }

    public void setData(GetOrderListData data) {
        this.data = data;
    }
}
