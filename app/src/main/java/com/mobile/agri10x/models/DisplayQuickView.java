package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisplayQuickView {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DisplayQuickViewData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DisplayQuickViewData> getData() {
        return data;
    }

    public void setData(List<DisplayQuickViewData> data) {
        this.data = data;
    }
}
