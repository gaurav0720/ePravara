package com.example.epravara;

public class Weight {

    String wDate, wWeight, wUnit;

    public String getwDate() {
        return wDate;
    }

    public void setwDate(String wDate) {
        this.wDate = wDate;
    }

    public String getwWeight() {
        return wWeight;
    }

    public void setwWeight(String wWeight) {
        this.wWeight = wWeight;
    }

    public String getwUnit() {
        return wUnit;
    }

    public void setwUnit(String wUnit) {
        this.wUnit = wUnit;
    }

    public Weight(String wDate, String wWeight, String wUnit) {
        this.wDate = wDate;
        this.wWeight = wWeight;
        this.wUnit = wUnit;
    }

    public Weight() {
    }
}
