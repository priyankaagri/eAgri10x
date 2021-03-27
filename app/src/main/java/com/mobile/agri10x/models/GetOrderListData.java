package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderListData {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bookingList")
    @Expose
    private List<BookingListFromOderList> bookingList = null;
    @SerializedName("checkoutList")
    @Expose
    private List<CheckoutListFromOrderList> checkoutList = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BookingListFromOderList> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingListFromOderList> bookingList) {
        this.bookingList = bookingList;
    }

    public List<CheckoutListFromOrderList> getCheckoutList() {
        return checkoutList;
    }

    public void setCheckoutList(List<CheckoutListFromOrderList> checkoutList) {
        this.checkoutList = checkoutList;
    }

}
