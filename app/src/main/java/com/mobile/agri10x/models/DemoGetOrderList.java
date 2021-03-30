package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemoGetOrderList {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DemoGetOrderListData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DemoGetOrderListData getData() {
        return data;
    }

    public void setData(DemoGetOrderListData data) {
        this.data = data;
    }
}
