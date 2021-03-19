package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getAddress {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<getAddressData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<getAddressData> getData() {
        return data;
    }

    public void setData(List<getAddressData> data) {
        this.data = data;
    }

}