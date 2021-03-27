package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData_FromOderLIst {

    @SerializedName("products")
    @Expose
    private List<Product_FromOderLIst> products = null;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;

    public List<Product_FromOderLIst> getProducts() {
        return products;
    }

    public void setProducts(List<Product_FromOderLIst> products) {
        this.products = products;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }
}
