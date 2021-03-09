package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCategories {
    @SerializedName("data")
    @Expose
    private List<GetCategoriesData> data = null;

    public List<GetCategoriesData> getData() {
        return data;
    }

    public void setData(List<GetCategoriesData> data) {
        this.data = data;
    }
}
