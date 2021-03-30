package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderListDatumBookingCartData {




        @SerializedName("products")
        @Expose
        private List<GetOrderListDatumBookingCartDataProduct> products = null;
        @SerializedName("subTotal")
        @Expose
        private Integer subTotal;

        public List<GetOrderListDatumBookingCartDataProduct> getProducts() {
            return products;
        }

        public void setProducts(List<GetOrderListDatumBookingCartDataProduct> products) {
            this.products = products;
        }

        public Integer getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(Integer subTotal) {
            this.subTotal = subTotal;
        }

    }

