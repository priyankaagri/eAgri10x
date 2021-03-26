package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRemoveFromWishlist {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetRemoveFromWishlData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetRemoveFromWishlData getData() {
        return data;
    }

    public void setData(GetRemoveFromWishlData data) {
        this.data = data;
    }
}

