package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductInWishListData {

    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("discount")
    @Expose
    private Boolean discount;
    @SerializedName("featureName")
    @Expose
    private List<Object> featureName = null;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("commodityID")
    @Expose
    private String commodityID;
    @SerializedName("variety")
    @Expose
    private String variety;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("wishListID")
    @Expose
    private String wishListID;
    @SerializedName("totalAvailable")
    @Expose
    private Integer totalAvailable;
    @SerializedName("ValidFrom")
    @Expose
    private String validFrom;
    @SerializedName("validTo")
    @Expose
    private String validTo;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("test")
    @Expose
    private Integer test;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getWishListID() {
        return wishListID;
    }

    public void setWishListID(String wishListID) {
        this.wishListID = wishListID;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }


}
