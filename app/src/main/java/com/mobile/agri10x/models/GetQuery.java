package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQuery {
    @SerializedName("query")

    @Expose
    private Querydata query;

    public Querydata getQuery() {
        return query;
    }

    public void setQuery(Querydata query) {
        this.query = query;
    }
}