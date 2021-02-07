package com.example.epravara;

public class Surgery {

    private String sDate, sName, sReason, sResults, sAftercare, sNotes,
            sOrderingPhysician, sPerformingSurgeon, sAnesthesiologist, sProcedureFacility;

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsReason() {
        return sReason;
    }

    public void setsReason(String sReason) {
        this.sReason = sReason;
    }

    public String getsResults() {
        return sResults;
    }

    public void setsResults(String sResults) {
        this.sResults = sResults;
    }

    public String getsAftercare() {
        return sAftercare;
    }

    public void setsAftercare(String sAftercare) {
        this.sAftercare = sAftercare;
    }

    public String getsNotes() {
        return sNotes;
    }

    public void setsNotes(String sNotes) {
        this.sNotes = sNotes;
    }

    public String getsOrderingPhysician() {
        return sOrderingPhysician;
    }

    public void setsOrderingPhysician(String sOrderingPhysician) {
        this.sOrderingPhysician = sOrderingPhysician;
    }

    public String getsPerformingSurgeon() {
        return sPerformingSurgeon;
    }

    public void setsPerformingSurgeon(String sPerformingSurgeon) {
        this.sPerformingSurgeon = sPerformingSurgeon;
    }

    public String getsAnesthesiologist() {
        return sAnesthesiologist;
    }

    public void setsAnesthesiologist(String sAnesthesiologist) {
        this.sAnesthesiologist = sAnesthesiologist;
    }

    public String getsProcedureFacility() {
        return sProcedureFacility;
    }

    public void setsProcedureFacility(String sProcedureFacility) {
        this.sProcedureFacility = sProcedureFacility;
    }

    public Surgery(String sDate, String sName, String sReason, String sResults, String sAftercare, String sNotes,
                   String sOrderingPhysician, String sPerformingSurgeon, String sAnesthesiologist, String sProcedureFacility) {
        this.sDate = sDate;
        this.sName = sName;
        this.sReason = sReason;
        this.sResults = sResults;
        this.sAftercare = sAftercare;
        this.sNotes = sNotes;
        this.sOrderingPhysician = sOrderingPhysician;
        this.sPerformingSurgeon = sPerformingSurgeon;
        this.sAnesthesiologist = sAnesthesiologist;
        this.sProcedureFacility = sProcedureFacility;
    }

    public Surgery() {
    }
}
