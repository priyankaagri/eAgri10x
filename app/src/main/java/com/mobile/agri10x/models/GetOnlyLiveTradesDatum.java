package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOnlyLiveTradesDatum {

    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("commodityID")
    @Expose
    private String commodityID;
    @SerializedName("city1")
    @Expose
    private String city1;
    @SerializedName("pricePerKg1")
    @Expose
    private Integer pricePerKg1;
    @SerializedName("city2")
    @Expose
    private String city2;
    @SerializedName("pricePerKg2")
    @Expose
    private Object pricePerKg2;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public Integer getPricePerKg1() {
        return pricePerKg1;
    }

    public void setPricePerKg1(Integer pricePerKg1) {
        this.pricePerKg1 = pricePerKg1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public Object getPricePerKg2() {
        return pricePerKg2;
    }

    public void setPricePerKg2(Object pricePerKg2) {
        this.pricePerKg2 = pricePerKg2;
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
