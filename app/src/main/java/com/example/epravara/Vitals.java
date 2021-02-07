package com.example.epravara;

public class Vitals {

    private String vDate, vOxygen, vTemp,vRRate;

    public String getvDate() {
        return vDate;
    }

    public void setvDate(String vDate) {
        this.vDate = vDate;
    }

    public String getvOxygen() {
        return vOxygen;
    }

    public void setvOxygen(String vOxygen) {
        this.vOxygen = vOxygen;
    }

    public String getvTemp() {
        return vTemp;
    }

    public void setvTemp(String vTemp) {
        this.vTemp = vTemp;
    }

    public String getvRRate() {
        return vRRate;
    }

    public void setvRRate(String vRRate) {
        this.vRRate = vRRate;
    }

    public Vitals(String vDate, String vOxygen, String vTemp, String vRRate) {
        this.vDate = vDate;
        this.vOxygen = vOxygen;
        this.vTemp = vTemp;
        this.vRRate = vRRate;
    }

    public Vitals() {
    }
}
