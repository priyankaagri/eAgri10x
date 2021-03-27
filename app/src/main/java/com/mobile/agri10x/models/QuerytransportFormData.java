package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuerytransportFormData {

    @SerializedName("tcName")
    @Expose
    private String tcName;
    @SerializedName("tfName")
    @Expose
    private String tfName;
    @SerializedName("tlName")
    @Expose
    private String tlName;
    @SerializedName("tContactNumber")
    @Expose
    private String tContactNumber;
    @SerializedName("temail")
    @Expose
    private String temail;
    @SerializedName("tstate")
    @Expose
    private String tstate;
    @SerializedName("ttype")
    @Expose
    private String ttype;
    @SerializedName("tweight")
    @Expose
    private String tweight;
    @SerializedName("tprice")
    @Expose
    private String tprice;
    @SerializedName("template")
    @Expose
    private String template;

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getTfName() {
        return tfName;
    }

    public void setTfName(String tfName) {
        this.tfName = tfName;
    }

    public String getTlName() {
        return tlName;
    }

    public void setTlName(String tlName) {
        this.tlName = tlName;
    }

    public String getTContactNumber() {
        return tContactNumber;
    }

    public void setTContactNumber(String tContactNumber) {
        this.tContactNumber = tContactNumber;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTstate() {
        return tstate;
    }

    public void setTstate(String tstate) {
        this.tstate = tstate;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getTweight() {
        return tweight;
    }

    public void setTweight(String tweight) {
        this.tweight = tweight;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
