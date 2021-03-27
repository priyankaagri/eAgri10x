package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDataFromOderList {

    @SerializedName("products")
    @Expose
    private List<ProductFromOderList> products = null;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;

    public List<ProductFromOderList> getProducts() {
        return products;
    }

    public void setProducts(List<ProductFromOderList> products) {
        this.products = products;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

}
