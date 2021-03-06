package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBookingDeatilCartData {

    @SerializedName("products")
    @Expose
    private List<GetBookingDeatilProduct> products = null;
    @SerializedName("subTotal")
    @Expose
    private double subTotal;

    public List<GetBookingDeatilProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GetBookingDeatilProduct> products) {
        this.products = products;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

}
