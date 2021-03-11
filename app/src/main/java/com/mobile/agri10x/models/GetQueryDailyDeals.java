package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQueryDailyDeals {
    @SerializedName("query")

    @Expose
    private QueryDailyDeals query;

    public QueryDailyDeals getQuery() {
        return query;
    }

    public void setQuery(QueryDailyDeals query) {
        this.query = query;
    }
}