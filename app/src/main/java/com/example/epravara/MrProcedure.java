package com.example.epravara;

public class MrProcedure {

    String pDate, pName, pReason;

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpReason() {
        return pReason;
    }

    public void setpReason(String pReason) {
        this.pReason = pReason;
    }

    public MrProcedure(String pDate, String pName, String pReason) {
        this.pDate = pDate;
        this.pName = pName;
        this.pReason = pReason;
    }

    public MrProcedure() {
    }
}
