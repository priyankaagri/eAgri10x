package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryCreateCheckOutData {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products")
    @Expose
    private List<GetProductsInCartProductData> products = null;
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

    public List<GetProductsInCartProductData> getProducts() {
        return products;
    }

    public void setProducts(List<GetProductsInCartProductData> products) {
        this.products = products;
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
