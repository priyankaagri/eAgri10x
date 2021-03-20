package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryCreatebookingCartData {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products")
    @Expose
    private List<GetProductsInCartProductData> products = null;
    @SerializedName("subTotal")
    @Expose
    private Double subTotal;
    @SerializedName("totalKgs")
    @Expose
    private Double totalKgs;

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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(Double totalKgs) {
        this.totalKgs = totalKgs;
    }
}
