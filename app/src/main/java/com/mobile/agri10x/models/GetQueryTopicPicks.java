package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQueryTopicPicks {

    @SerializedName("query")
    @Expose
    private QueryTopicks query;

    public QueryTopicks getQuery() {
        return query;
    }

    public void setQuery(QueryTopicks query) {
        this.query = query;
    }

}