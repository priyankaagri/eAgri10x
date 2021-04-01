
package com.mobile.agri10x.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetUserBalanceData {

    @SerializedName("CurrentBalance")
    @Expose
    private double currentBalance;
    @SerializedName("BlockedAmount")
    @Expose
    private double blockedAmount;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(double blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
