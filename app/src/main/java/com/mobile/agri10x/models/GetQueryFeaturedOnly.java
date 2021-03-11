package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQueryFeaturedOnly {

    @SerializedName("query")
    @Expose
    private QueryFeatureOnly query;

    public QueryFeatureOnly getQuery() {
        return query;
    }

    public void setQuery(QueryFeatureOnly query) {
        this.query = query;
    }
}
