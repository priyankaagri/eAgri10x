package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuerytransportForm {

    @SerializedName("data")
    @Expose
    private QuerytransportFormData data;

    public QuerytransportFormData getData() {
        return data;
    }

    public void setData(QuerytransportFormData data) {
        this.data = data;
    }
}
