package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVarietiesDatum {
    @SerializedName("Variety")
    @Expose
    private String variety;
    @SerializedName("isTrade")
    @Expose
    private Boolean isTrade;
    @SerializedName("isRequest")
    @Expose
    private Boolean isRequest;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("LastUpdatedBy")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("CommodityRef")
    @Expose
    private String commodityRef;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Boolean getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(Boolean isTrade) {
        this.isTrade = isTrade;
    }

    public Boolean getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(Boolean isRequest) {
        this.isRequest = isRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCommodityRef() {
        return commodityRef;
    }

    public void setCommodityRef(String commodityRef) {
        this.commodityRef = commodityRef;
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
