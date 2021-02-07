package com.example.epravara;

public class Pharmacy {

    String pharmacyName,pharmacyPhone,pharmacyEmail,pharmacyAddress,pharmacyCity,pharmacyState,pharmacistName;

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyCity() {
        return pharmacyCity;
    }

    public void setPharmacyCity(String pharmacyCity) {
        this.pharmacyCity = pharmacyCity;
    }

    public String getPharmacyState() {
        return pharmacyState;
    }

    public void setPharmacyState(String pharmacyState) {
        this.pharmacyState = pharmacyState;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public Pharmacy(String pharmacyName, String pharmacyPhone, String pharmacyEmail
            , String pharmacyAddress, String pharmacyCity, String pharmacyState, String pharmacistName) {
        this.pharmacyName = pharmacyName;
        this.pharmacyPhone = pharmacyPhone;
        this.pharmacyEmail = pharmacyEmail;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyCity = pharmacyCity;
        this.pharmacyState = pharmacyState;
        this.pharmacistName = pharmacistName;
    }

    public Pharmacy() {
    }
}
