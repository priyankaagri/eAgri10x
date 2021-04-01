
package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserBalance {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetUserBalanceData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetUserBalanceData getData() {
        return data;
    }

    public void setData(GetUserBalanceData data) {
        this.data = data;
    }

}
