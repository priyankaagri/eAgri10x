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
    private double subTotal;
    @SerializedName("totalKgs")
    @Expose
    private double totalKgs;

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

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(double totalKgs) {
        this.totalKgs = totalKgs;
    }
}
