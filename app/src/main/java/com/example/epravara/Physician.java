package com.example.epravara;

public class Physician {

    String pName, pSpeciality, pPhone, pEmail, pAddress, pCity, pState, pPincode;

    public Physician(String pName, String pSpeciality, String pPhone, String pEmail,
                     String pAddress, String pCity, String pState, String pPincode) {
        this.pName = pName;
        this.pSpeciality = pSpeciality;
        this.pPhone = pPhone;
        this.pEmail = pEmail;
        this.pAddress = pAddress;
        this.pCity = pCity;
        this.pState = pState;
        this.pPincode = pPincode;
    }

    public Physician() {
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpSpeciality() {
        return pSpeciality;
    }

    public void setpSpeciality(String pSpeciality) {
        this.pSpeciality = pSpeciality;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getpPincode() {
        return pPincode;
    }

    public void setpPincode(String pPincode) {
        this.pPincode = pPincode;
    }
}
