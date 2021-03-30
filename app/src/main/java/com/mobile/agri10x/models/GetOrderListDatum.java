package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderListDatum {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bookingList")
    @Expose
    private List<GetOrderListDatumBooking> bookingList = null;
    @SerializedName("checkoutList")
    @Expose
    private List<GetOrderListDatumCheckout> checkoutList = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GetOrderListDatumBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<GetOrderListDatumBooking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<GetOrderListDatumCheckout> getCheckoutList() {
        return checkoutList;
    }

    public void setCheckoutList(List<GetOrderListDatumCheckout> checkoutList) {
        this.checkoutList = checkoutList;
    }
}
