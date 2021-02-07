package com.example.epravara;

public class Illness {

    String illnessDate, illnessType, illnessName, otherType, otherName, illnessHappened;

    public Illness() {
    }

    public Illness(String illnessDate, String illnessType, String illnessName,
                   String otherType, String otherName, String illnessHappened) {
        this.illnessDate = illnessDate;
        this.illnessType = illnessType;
        this.illnessName = illnessName;
        this.otherType = otherType;
        this.otherName = otherName;
        this.illnessHappened = illnessHappened;
    }

    public String getIllnessDate() {
        return illnessDate;
    }

    public void setIllnessDate(String illnessDate) {
        this.illnessDate = illnessDate;
    }

    public String getIllnessType() {
        return illnessType;
    }

    public void setIllnessType(String illnessType) {
        this.illnessType = illnessType;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getIllnessHappened() {
        return illnessHappened;
    }

    public void setIllnessHappened(String illnessHappened) {
        this.illnessHappened = illnessHappened;
    }
}
