package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQueryTopic {

    @SerializedName("query")
    @Expose
    private QueryTopicData query;

    public QueryTopicData getQuery() {
        return query;
    }

    public void setQuery(QueryTopicData query) {
        this.query = query;
    }

}