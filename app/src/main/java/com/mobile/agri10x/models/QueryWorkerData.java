package com.mobile.agri10x.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryWorkerData {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("workerphone")
    @Expose
    private String workerphone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("reaper")
    @Expose
    private Boolean reaper;
    @SerializedName("sower")
    @Expose
    private Boolean sower;
    @SerializedName("loader")
    @Expose
    private Boolean loader;
    @SerializedName("template")
    @Expose
    private String template;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkerphone() {
        return workerphone;
    }

    public void setWorkerphone(String workerphone) {
        this.workerphone = workerphone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getReaper() {
        return reaper;
    }

    public void setReaper(Boolean reaper) {
        this.reaper = reaper;
    }

    public Boolean getSower() {
        return sower;
    }

    public void setSower(Boolean sower) {
        this.sower = sower;
    }

    public Boolean getLoader() {
        return loader;
    }

    public void setLoader(Boolean loader) {
        this.loader = loader;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
