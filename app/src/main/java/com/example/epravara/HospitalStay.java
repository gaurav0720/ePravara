package com.example.epravara;

public class HospitalStay {

    String hsDate, hsName, hsReason;

    public String getHsDate() {
        return hsDate;
    }

    public void setHsDate(String hsDate) {
        this.hsDate = hsDate;
    }

    public String getHsName() {
        return hsName;
    }

    public void setHsName(String hsName) {
        this.hsName = hsName;
    }

    public String getHsReason() {
        return hsReason;
    }

    public void setHsReason(String hsReason) {
        this.hsReason = hsReason;
    }

    public HospitalStay(String hsDate, String hsName, String hsReason) {
        this.hsDate = hsDate;
        this.hsName = hsName;
        this.hsReason = hsReason;
    }

    public HospitalStay() {
    }
}
