package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCommodityDatum {
    @SerializedName("isTrade")
    @Expose
    private Boolean isTrade;
    @SerializedName("isRequest")
    @Expose
    private Boolean isRequest;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("Commodity")
    @Expose
    private String commodity;
    @SerializedName("LastUpdatedBy")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

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

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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
