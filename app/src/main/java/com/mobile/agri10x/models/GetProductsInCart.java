package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductsInCart {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<GetProductsInCartData> data = null;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;
    @SerializedName("totalKgs")
    @Expose
    private Integer totalKgs;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetProductsInCartData> getData() {
        return data;
    }

    public void setData(List<GetProductsInCartData> data) {
        this.data = data;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(Integer totalKgs) {
        this.totalKgs = totalKgs;
    }

}
