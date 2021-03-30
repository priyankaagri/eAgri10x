package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderListDatumCheckoutCartDataProduct {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("commodityID")
    @Expose
    private String commodityID;
    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("userProductID")
    @Expose
    private String userProductID;
    @SerializedName("totalAvailable")
    @Expose
    private Integer totalAvailable;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserProductID() {
        return userProductID;
    }

    public void setUserProductID(String userProductID) {
        this.userProductID = userProductID;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
    }
}
