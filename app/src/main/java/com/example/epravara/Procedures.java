package com.example.epravara;

public class Procedures {

    private String pDate, pName, pReason, pResults, pNotes,
            pOrderingPhysician, pPerformingSurgeon, pProcedureFacility;

    public Procedures(String pDate, String pName, String pReason, String pResults,
                      String pNotes, String pOrderingPhysician, String pPerformingSurgeon, String pProcedureFacility) {
        this.pDate = pDate;
        this.pName = pName;
        this.pReason = pReason;
        this.pResults = pResults;
        this.pNotes = pNotes;
        this.pOrderingPhysician = pOrderingPhysician;
        this.pPerformingSurgeon = pPerformingSurgeon;
        this.pProcedureFacility = pProcedureFacility;
    }

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

    public String getpResults() {
        return pResults;
    }

    public void setpResults(String pResults) {
        this.pResults = pResults;
    }

    public String getpNotes() {
        return pNotes;
    }

    public void setpNotes(String pNotes) {
        this.pNotes = pNotes;
    }

    public String getpOrderingPhysician() {
        return pOrderingPhysician;
    }

    public void setpOrderingPhysician(String pOrderingPhysician) {
        this.pOrderingPhysician = pOrderingPhysician;
    }

    public String getpPerformingSurgeon() {
        return pPerformingSurgeon;
    }

    public void setpPerformingSurgeon(String pPerformingSurgeon) {
        this.pPerformingSurgeon = pPerformingSurgeon;
    }

    public String getpProcedureFacility() {
        return pProcedureFacility;
    }

    public void setpProcedureFacility(String pProcedureFacility) {
        this.pProcedureFacility = pProcedureFacility;
    }

    public Procedures() {
    }
}
