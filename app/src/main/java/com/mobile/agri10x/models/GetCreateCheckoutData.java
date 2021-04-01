package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCreateCheckoutData {


    @SerializedName("Cart_Data")
    @Expose
    private GetCreateCheckoutDataCart cartData;
    @SerializedName("Order_Amount")
    @Expose
    private double orderAmount;
    @SerializedName("Order_Notes")
    @Expose
    private String orderNotes;
    @SerializedName("Payment_Date")
    @Expose
    private Object paymentDate;
    @SerializedName("PackagingDetails")
    @Expose
    private String packagingDetails;
    @SerializedName("Razorpay_Order_ID")
    @Expose
    private String razorpayOrderID;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Billing_Address_ID")
    @Expose
    private String billingAddressID;
    @SerializedName("Shipping_Address_ID")
    @Expose
    private String shippingAddressID;
    @SerializedName("Payment_Status")
    @Expose
    private Boolean paymentStatus;
    @SerializedName("DeliveryContactPerson")
    @Expose
    private String deliveryContactPerson;
    @SerializedName("DeliveryMobileNo")
    @Expose
    private Integer deliveryMobileNo;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public GetCreateCheckoutDataCart getCartData() {
        return cartData;
    }

    public void setCartData(GetCreateCheckoutDataCart cartData) {
        this.cartData = cartData;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public Object getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Object paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPackagingDetails() {
        return packagingDetails;
    }

    public void setPackagingDetails(String packagingDetails) {
        this.packagingDetails = packagingDetails;
    }

    public String getRazorpayOrderID() {
        return razorpayOrderID;
    }

    public void setRazorpayOrderID(String razorpayOrderID) {
        this.razorpayOrderID = razorpayOrderID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDeliveryContactPerson() {
        return deliveryContactPerson;
    }

    public void setDeliveryContactPerson(String deliveryContactPerson) {
        this.deliveryContactPerson = deliveryContactPerson;
    }

    public Integer getDeliveryMobileNo() {
        return deliveryMobileNo;
    }

    public void setDeliveryMobileNo(Integer deliveryMobileNo) {
        this.deliveryMobileNo = deliveryMobileNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}

