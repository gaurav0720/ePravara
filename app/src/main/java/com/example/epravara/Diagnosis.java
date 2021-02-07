package com.example.epravara;

public class Diagnosis {

    private String dDate, dDetails, dCause, dTPlan, dNotes, dPhysician;

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getdDetails() {
        return dDetails;
    }

    public void setdDetails(String dDetails) {
        this.dDetails = dDetails;
    }

    public String getdCause() {
        return dCause;
    }

    public void setdCause(String dCause) {
        this.dCause = dCause;
    }

    public String getdTPlan() {
        return dTPlan;
    }

    public void setdTPlan(String dTPlan) {
        this.dTPlan = dTPlan;
    }

    public String getdNotes() {
        return dNotes;
    }

    public void setdNotes(String dNotes) {
        this.dNotes = dNotes;
    }

    public String getdPhysician() {
        return dPhysician;
    }

    public void setdPhysician(String dPhysician) {
        this.dPhysician = dPhysician;
    }

    public Diagnosis() {
    }

    public Diagnosis(String dDate, String dDetails, String dCause, String dTPlan, String dNotes, String dPhysician) {
        this.dDate = dDate;
        this.dDetails = dDetails;
        this.dCause = dCause;
        this.dTPlan = dTPlan;
        this.dNotes = dNotes;
        this.dPhysician = dPhysician;
    }
}
