package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddMoney {

    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("initiatePayment")
    @Expose
    private Boolean initiatePayment;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getInitiatePayment() {
        return initiatePayment;
    }

    public void setInitiatePayment(Boolean initiatePayment) {
        this.initiatePayment = initiatePayment;
    }

}
