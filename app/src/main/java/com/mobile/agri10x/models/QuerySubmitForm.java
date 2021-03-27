package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuerySubmitForm {
    @SerializedName("data")
    @Expose
    private QuerySubmitData data;

    public QuerySubmitData getData() {
        return data;
    }

    public void setData(QuerySubmitData data) {
        this.data = data;
    }
}
