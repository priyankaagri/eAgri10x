package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetADDWishData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("m_OrderID")
    @Expose
    private String mOrderID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMOrderID() {
        return mOrderID;
    }

    public void setMOrderID(String mOrderID) {
        this.mOrderID = mOrderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
