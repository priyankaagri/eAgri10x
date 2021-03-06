
package com.mobile.agri10x.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetOrderList {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<GetOrderListDatum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetOrderListDatum> getData() {
        return data;
    }

    public void setData(List<GetOrderListDatum> data) {
        this.data = data;
    }

}
