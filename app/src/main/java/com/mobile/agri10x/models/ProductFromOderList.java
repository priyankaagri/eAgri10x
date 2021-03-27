package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductFromOderList {
    @SerializedName("commodityID")
    @Expose
    private String commodityID;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("totalAvailable")
    @Expose
    private Integer totalAvailable;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userProductID")
    @Expose
    private String userProductID;
    @SerializedName("variety")
    @Expose
    private String variety;
    @SerializedName("weight")
    @Expose
    private Integer weight;

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserProductID() {
        return userProductID;
    }

    public void setUserProductID(String userProductID) {
        this.userProductID = userProductID;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
