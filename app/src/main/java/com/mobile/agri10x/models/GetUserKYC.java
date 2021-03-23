package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserKYC {

    @SerializedName("Passport")
    @Expose
    private String passport;
    @SerializedName("VoterID")
    @Expose
    private String voterID;
    @SerializedName("Aadhar")
    @Expose
    private String aadhar;
    @SerializedName("Pancard")
    @Expose
    private String pancard;
    @SerializedName("Other")
    @Expose
    private String other;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getVoterID() {
        return voterID;
    }

    public void setVoterID(String voterID) {
        this.voterID = voterID;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}