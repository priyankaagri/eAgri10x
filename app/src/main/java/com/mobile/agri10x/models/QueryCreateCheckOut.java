package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryCreateCheckOut {
    @SerializedName("Userid")
    @Expose
    private String userid;
    @SerializedName("orderAmount")
    @Expose
    private Integer orderAmount;
    @SerializedName("billingAddressID")
    @Expose
    private String billingAddressID;
    @SerializedName("shippingAddressID")
    @Expose
    private String shippingAddressID;
    @SerializedName("orderNotes")
    @Expose
    private String orderNotes;
    @SerializedName("DeliveryContactPerson")
    @Expose
    private String deliveryContactPerson;
    @SerializedName("DeliveryMobileNo")
    @Expose
    private String deliveryMobileNo;
    @SerializedName("PackagingDetails")
    @Expose
    private String packagingDetails;
    @SerializedName("cartData")
    @Expose
    private QueryCreateCheckOutData cartData;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getBillingAddressID() {
        return billingAddressID;
    }

    public void setBillingAddressID(String billingAddressID) {
        this.billingAddressID = billingAddressID;
    }

    public String getShippingAddressID() {
        return shippingAddressID;
    }

    public void setShippingAddressID(String shippingAddressID) {
        this.shippingAddressID = shippingAddressID;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getDeliveryContactPerson() {
        return deliveryContactPerson;
    }

    public void setDeliveryContactPerson(String deliveryContactPerson) {
        this.deliveryContactPerson = deliveryContactPerson;
    }

    public String getDeliveryMobileNo() {
        return deliveryMobileNo;
    }

    public void setDeliveryMobileNo(String deliveryMobileNo) {
        this.deliveryMobileNo = deliveryMobileNo;
    }

    public String getPackagingDetails() {
        return packagingDetails;
    }

    public void setPackagingDetails(String packagingDetails) {
        this.packagingDetails = packagingDetails;
    }

    public QueryCreateCheckOutData getCartData() {
        return cartData;
    }

    public void setCartData(QueryCreateCheckOutData cartData) {
        this.cartData = cartData;
    }

}
