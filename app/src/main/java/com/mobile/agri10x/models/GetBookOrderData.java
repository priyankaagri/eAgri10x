package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookOrderData {

    @SerializedName("razorpayOrderid")
    @Expose
    private String razorpayOrderid;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("initiatePayment")
    @Expose
    private Boolean initiatePayment;

    public String getRazorpayOrderid() {
        return razorpayOrderid;
    }

    public void setRazorpayOrderid(String razorpayOrderid) {
        this.razorpayOrderid = razorpayOrderid;
    }

    public double getAmount() {
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

    public Boolean getInitiatePayment() {
        return initiatePayment;
    }

    public void setInitiatePayment(Boolean initiatePayment) {
        this.initiatePayment = initiatePayment;
    }
}
