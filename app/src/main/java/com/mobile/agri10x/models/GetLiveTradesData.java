package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLiveTradesData {

    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("discount")
    @Expose
    private Boolean discount;
    @SerializedName("featureName")
    @Expose
    private List<Object> featureName = null;
    @SerializedName("commodityName")
    @Expose
    private String commodityName;
    @SerializedName("varietyName")
    @Expose
    private String varietyName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("commodityID")
    @Expose
    private String commodityID;
    @SerializedName("dealOfTheDay")
    @Expose
    private Boolean dealOfTheDay;
    @SerializedName("freshProducts")
    @Expose
    private Boolean freshProducts;
    @SerializedName("topPicks")
    @Expose
    private Boolean topPicks;
    @SerializedName("otherMentions")
    @Expose
    private Boolean otherMentions;
    @SerializedName("discounted")
    @Expose
    private Boolean discounted;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("pricePerLot")
    @Expose
    private Double pricePerLot;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("validFrom")
    @Expose
    private String validFrom;
    @SerializedName("validTo")
    @Expose
    private String validTo;
    @SerializedName("totalAvailable")
    @Expose
    private Integer totalAvailable;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("lotSize")
    @Expose
    private Integer lotSize;

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public List<Object> getFeatureName() {
        return featureName;
    }

    public void setFeatureName(List<Object> featureName) {
        this.featureName = featureName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public Boolean getDealOfTheDay() {
        return dealOfTheDay;
    }

    public void setDealOfTheDay(Boolean dealOfTheDay) {
        this.dealOfTheDay = dealOfTheDay;
    }

    public Boolean getFreshProducts() {
        return freshProducts;
    }

    public void setFreshProducts(Boolean freshProducts) {
        this.freshProducts = freshProducts;
    }

    public Boolean getTopPicks() {
        return topPicks;
    }

    public void setTopPicks(Boolean topPicks) {
        this.topPicks = topPicks;
    }

    public Boolean getOtherMentions() {
        return otherMentions;
    }

    public void setOtherMentions(Boolean otherMentions) {
        this.otherMentions = otherMentions;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getPricePerLot() {
        return pricePerLot;
    }

    public void setPricePerLot(Double pricePerLot) {
        this.pricePerLot = pricePerLot;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
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

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

}
