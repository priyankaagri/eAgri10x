package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryTopicData{
    @SerializedName("topPicks")
    @Expose
    private Boolean topPicks;

    public Boolean getTopPicks() {
        return topPicks;
    }

    public void setTopPicks(Boolean topPicks) {
        this.topPicks = topPicks;
    }

}
