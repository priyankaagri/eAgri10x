package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCreateCheckoutCartData {
    @SerializedName("products")
    @Expose
    private List<GetCreateCheckoutCartProduct> products = null;
    @SerializedName("subTotal")
    @Expose
    private double subTotal;

    public List<GetCreateCheckoutCartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GetCreateCheckoutCartProduct> products) {
        this.products = products;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }
}
