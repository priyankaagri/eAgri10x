package com.mobile.agri10x.Fragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryWearHouseFormData {
    @SerializedName("wfName")
    @Expose
    private String wfName;
    @SerializedName("wlName")
    @Expose
    private String wlName;
    @SerializedName("wContactNumber")
    @Expose
    private String wContactNumber;
    @SerializedName("wemail")
    @Expose
    private String wemail;
    @SerializedName("wstate")
    @Expose
    private String wstate;
    @SerializedName("wfeature")
    @Expose
    private String wfeature;
    @SerializedName("wstock")
    @Expose
    private String wstock;
    @SerializedName("template")
    @Expose
    private String template;

    public String getWfName() {
        return wfName;
    }

    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    public String getWlName() {
        return wlName;
    }

    public void setWlName(String wlName) {
        this.wlName = wlName;
    }

    public String getWContactNumber() {
        return wContactNumber;
    }

    public void setWContactNumber(String wContactNumber) {
        this.wContactNumber = wContactNumber;
    }

    public String getWemail() {
        return wemail;
    }

    public void setWemail(String wemail) {
        this.wemail = wemail;
    }

    public String getWstate() {
        return wstate;
    }

    public void setWstate(String wstate) {
        this.wstate = wstate;
    }

    public String getWfeature() {
        return wfeature;
    }

    public void setWfeature(String wfeature) {
        this.wfeature = wfeature;
    }

    public String getWstock() {
        return wstock;
    }

    public void setWstock(String wstock) {
        this.wstock = wstock;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
