package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingListData {


    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("bookingID")
    @Expose
    private String bookingID;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("totalAmount")
    @Expose
    private double totalAmount;
    @SerializedName("paidAmount")
    @Expose
    private double paidAmount;
    @SerializedName("pendingAmount")
    @Expose
    private double pendingAmount;
    @SerializedName("deadline")
    @Expose
    private String deadline;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
