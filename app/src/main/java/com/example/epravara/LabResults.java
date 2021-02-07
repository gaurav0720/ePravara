package com.example.epravara;

public class LabResults {

    private String lrDate, lrName, lrValue, lrUnit, lrPhysician;

    public String getLrDate() {
        return lrDate;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

    public String getLrName() {
        return lrName;
    }

    public void setLrName(String lrName) {
        this.lrName = lrName;
    }

    public String getLrValue() {
        return lrValue;
    }

    public void setLrValue(String lrValue) {
        this.lrValue = lrValue;
    }

    public String getLrUnit() {
        return lrUnit;
    }

    public void setLrUnit(String lrUnit) {
        this.lrUnit = lrUnit;
    }

    public String getLrPhysician() {
        return lrPhysician;
    }

    public void setLrPhysician(String lrPhysician) {
        this.lrPhysician = lrPhysician;
    }

    public LabResults(String lrDate, String lrName, String lrValue, String lrUnit, String lrPhysician) {
        this.lrDate = lrDate;
        this.lrName = lrName;
        this.lrValue = lrValue;
        this.lrUnit = lrUnit;
        this.lrPhysician = lrPhysician;
    }

    public LabResults() {
    }
}
