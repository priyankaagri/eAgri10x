package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHomeProduct {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("varietyName")
    @Expose
    private String varietyName;
    @SerializedName("pricePerLot")
    @Expose
    private Integer pricePerLot;
    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("totalAvailable")
    @Expose
    private Integer totalAvailable;
    @SerializedName("orderID")
    @Expose
    private String orderID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public Integer getPricePerLot() {
        return pricePerLot;
    }

    public void setPricePerLot(Integer pricePerLot) {
        this.pricePerLot = pricePerLot;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
