package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryCreatebooking {
    @SerializedName("Userid")
    @Expose
    private String userid;
    @SerializedName("billingAddressID")
    @Expose
    private String billingAddressID;
    @SerializedName("shippingAddressID")
    @Expose
    private String shippingAddressID;
    @SerializedName("orderNotes")
    @Expose
    private String orderNotes;
    @SerializedName("percentage")
    @Expose
    private Double percentage;
    @SerializedName("bookingAmount")
    @Expose
    private Double bookingAmount;
    @SerializedName("pendingAmount")
    @Expose
    private Double pendingAmount;
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
    private QueryCreatebookingCartData cartData;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(Double bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public Double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(Double pendingAmount) {
        this.pendingAmount = pendingAmount;
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

    public QueryCreatebookingCartData getCartData() {
        return cartData;
    }

    public void setCartData(QueryCreatebookingCartData cartData) {
        this.cartData = cartData;
    }

}
