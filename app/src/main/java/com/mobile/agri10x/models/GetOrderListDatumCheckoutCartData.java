package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderListDatumCheckoutCartData {

    @SerializedName("products")
    @Expose
    private List<GetOrderListDatumCheckoutCartDataProduct> products = null;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;

    public List<GetOrderListDatumCheckoutCartDataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GetOrderListDatumCheckoutCartDataProduct> products) {
        this.products = products;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }
}
