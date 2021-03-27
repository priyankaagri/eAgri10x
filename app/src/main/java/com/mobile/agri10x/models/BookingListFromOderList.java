package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingListFromOderList {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("PackagingDetails")
    @Expose
    private String packagingDetails;
    @SerializedName("Razorpay_Order_ID")
    @Expose
    private String razorpayOrderID;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Billing_Address_ID")
    @Expose
    private String billingAddressID;
    @SerializedName("Shipping_Address_ID")
    @Expose
    private String shippingAddressID;
    @SerializedName("Order_Notes")
    @Expose
    private String orderNotes;
    @SerializedName("Percentage")
    @Expose
    private Double percentage;
    @SerializedName("Booking_Amount")
    @Expose
    private Double bookingAmount;
    @SerializedName("Pending_Amount")
    @Expose
    private Double pendingAmount;
    @SerializedName("Partial_Payment")
    @Expose
    private Boolean partialPayment;
    @SerializedName("Complete_Payment")
    @Expose
    private Boolean completePayment;
    @SerializedName("Partial_Payment_Date")
    @Expose
    private Object partialPaymentDate;
    @SerializedName("Complete_Payment_Date")
    @Expose
    private Object completePaymentDate;
    @SerializedName("Cart_Data")
    @Expose
    private CartDataFromOderList cartData;
    @SerializedName("DeliveryContactPerson")
    @Expose
    private String deliveryContactPerson;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getPartialPayment() {
        return partialPayment;
    }

    public void setPartialPayment(Boolean partialPayment) {
        this.partialPayment = partialPayment;
    }

    public Boolean getCompletePayment() {
        return completePayment;
    }

    public void setCompletePayment(Boolean completePayment) {
        this.completePayment = completePayment;
    }

    public Object getPartialPaymentDate() {
        return partialPaymentDate;
    }

    public void setPartialPaymentDate(Object partialPaymentDate) {
        this.partialPaymentDate = partialPaymentDate;
    }

    public Object getCompletePaymentDate() {
        return completePaymentDate;
    }

    public void setCompletePaymentDate(Object completePaymentDate) {
        this.completePaymentDate = completePaymentDate;
    }

    public CartDataFromOderList getCartData() {
        return cartData;
    }

    public void setCartData(CartDataFromOderList cartData) {
        this.cartData = cartData;
    }

    public String getDeliveryContactPerson() {
        return deliveryContactPerson;
    }

    public void setDeliveryContactPerson(String deliveryContactPerson) {
        this.deliveryContactPerson = deliveryContactPerson;
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
