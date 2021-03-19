package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserByID {
    @SerializedName("data")
    @Expose
    private GetUserByIDData data;

    public GetUserByIDData getData() {
        return data;
    }

    public void setData(GetUserByIDData data) {
        this.data = data;
    }
}
