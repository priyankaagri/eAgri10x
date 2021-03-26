package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRemoveFromWishlData {
    @SerializedName("n")
    @Expose
    private Integer n;
    @SerializedName("ok")
    @Expose
    private Integer ok;
    @SerializedName("deletedCount")
    @Expose
    private Integer deletedCount;

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public Integer getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(Integer deletedCount) {
        this.deletedCount = deletedCount;
    }
}
