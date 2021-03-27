package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryWorkerForm {
    @SerializedName("data")
    @Expose
    private QueryWorkerData data;

    public QueryWorkerData getData() {
        return data;
    }

    public void setData(QueryWorkerData data) {
        this.data = data;
    }
}
