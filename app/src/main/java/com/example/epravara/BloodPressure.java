package com.example.epravara;

public class BloodPressure {

    String bpDate, bpSystolic, bpDiastolic, bpPulse, bpArm;

    public String getBpDate() {
        return bpDate;
    }

    public void setBpDate(String bpDate) {
        this.bpDate = bpDate;
    }

    public String getBpSystolic() {
        return bpSystolic;
    }

    public void setBpSystolic(String bpSystolic) {
        this.bpSystolic = bpSystolic;
    }

    public String getBpDiastolic() {
        return bpDiastolic;
    }

    public void setBpDiastolic(String bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }

    public String getBpPulse() {
        return bpPulse;
    }

    public void setBpPulse(String bpPulse) {
        this.bpPulse = bpPulse;
    }

    public String getBpArm() {
        return bpArm;
    }

    public void setBpArm(String bpArm) {
        this.bpArm = bpArm;
    }

    public BloodPressure(String bpDate, String bpSystolic, String bpDiastolic, String bpPulse, String bpArm) {
        this.bpDate = bpDate;
        this.bpSystolic = bpSystolic;
        this.bpDiastolic = bpDiastolic;
        this.bpPulse = bpPulse;
        this.bpArm = bpArm;
    }

    public BloodPressure() {
    }
}
