package com.example.emailapi.Entity;

import androidx.annotation.Nullable;

public class Safety {
    private String SafetyId;
    private Boolean car1;
    private Boolean criminal1;
    private String adult;
    private String garden;
    private String hoursAlone;
    private String property;
    private String otherAnimal;
    private String ussid;

    public Safety() {
        this.SafetyId="";
        this.car1=false;
        this.criminal1=false;
        this.adult="";
        this.garden="";
        this.hoursAlone="";
        this.property="";
        this.otherAnimal="";
        this.ussid="";
    }

    public Safety(String SafetyId, Boolean car1, Boolean criminal1, String adult, String garden, String hoursAlone, String property, String otherAnimal, String ussid) {
        this.SafetyId=SafetyId;
        this.car1=car1;
        this.criminal1=criminal1;
        this.adult=adult;
        this.garden=garden;
        this.hoursAlone=hoursAlone;
        this.property=property;
        this.otherAnimal=otherAnimal;
        this.ussid=ussid;
    }

    public String getUssid() {
        return ussid;
    }

    public void setUssid(String ussid) {
        this.ussid = ussid;
    }

    public String getOtherAnimal() {
        return otherAnimal;
    }

    public void setOtherAnimal(String otherAnimal) {
        this.otherAnimal = otherAnimal;
    }

    public Boolean getCar() {
        return car1;
    }

    public void setCar(Boolean car1) {
        this.car1 = car1;
    }

    public Boolean getCriminal() {
        return criminal1;
    }

    public void setCriminal(Boolean criminal1) {
        this.criminal1 = criminal1;
    }

    public String getSafetyId() {
        return SafetyId;
    }

    public void setSafetyId(String safetyId) {
        SafetyId = safetyId;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public String getHoursAlone() {
        return hoursAlone;
    }

    public void setHoursAlone(String hoursAlone) {
        this.hoursAlone = hoursAlone;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

}
