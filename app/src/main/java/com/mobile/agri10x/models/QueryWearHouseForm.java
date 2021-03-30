package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryWearHouseForm {
    @SerializedName("data")
    @Expose
    private QueryWearHouseFormData data;

    public QueryWearHouseFormData getData() {
        return data;
    }

    public void setData(QueryWearHouseFormData data) {
        this.data = data;
    }
}
