package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCreateBookingCartData {

    @SerializedName("products")
    @Expose
    private List<GetCreateBookCartDataProduct> products = null;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;

    public List<GetCreateBookCartDataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GetCreateBookCartDataProduct> products) {
        this.products = products;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }
}
