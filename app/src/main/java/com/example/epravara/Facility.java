package com.example.epravara;

public class Facility {

    String facilityName, facilityType, facilityPhone, facilityEmail, facilityAddress,
            facilityCity, facilityState, facilityContactPerson, facilityNotes;

    public Facility() {
    }

    public Facility(String facilityName, String facilityType, String facilityPhone,
                    String facilityEmail, String facilityAddress, String facilityCity,
                    String facilityState, String facilityContactPerson, String facilityNotes) {
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.facilityPhone = facilityPhone;
        this.facilityEmail = facilityEmail;
        this.facilityAddress = facilityAddress;
        this.facilityCity = facilityCity;
        this.facilityState = facilityState;
        this.facilityContactPerson = facilityContactPerson;
        this.facilityNotes = facilityNotes;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityPhone() {
        return facilityPhone;
    }

    public void setFacilityPhone(String facilityPhone) {
        this.facilityPhone = facilityPhone;
    }

    public String getFacilityEmail() {
        return facilityEmail;
    }

    public void setFacilityEmail(String facilityEmail) {
        this.facilityEmail = facilityEmail;
    }

    public String getFacilityAddress() {
        return facilityAddress;
    }

    public void setFacilityAddress(String facilityAddress) {
        this.facilityAddress = facilityAddress;
    }

    public String getFacilityCity() {
        return facilityCity;
    }

    public void setFacilityCity(String facilityCity) {
        this.facilityCity = facilityCity;
    }

    public String getFacilityState() {
        return facilityState;
    }

    public void setFacilityState(String facilityState) {
        this.facilityState = facilityState;
    }

    public String getFacilityContactPerson() {
        return facilityContactPerson;
    }

    public void setFacilityContactPerson(String facilityContactPerson) {
        this.facilityContactPerson = facilityContactPerson;
    }

    public String getFacilityNotes() {
        return facilityNotes;
    }

    public void setFacilityNotes(String facilityNotes) {
        this.facilityNotes = facilityNotes;
    }
}
