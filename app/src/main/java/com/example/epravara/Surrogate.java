package com.example.epravara;

public class Surrogate {
    private String surrogateName, surrogateRelation, surrogatePhone, surrogateAddress, surrogateCity, surrogateState, surrogatePincode;

    public String getSurrogateName() {
        return surrogateName;
    }

    public void setSurrogateName(String surrogateName) {
        this.surrogateName = surrogateName;
    }

    public String getSurrogateRelation() {
        return surrogateRelation;
    }

    public void setSurrogateRelation(String surrogateRelation) {
        this.surrogateRelation = surrogateRelation;
    }

    public String getSurrogatePhone() {
        return surrogatePhone;
    }

    public void setSurrogatePhone(String surrogatePhone) {
        this.surrogatePhone = surrogatePhone;
    }

    public String getSurrogateAddress() {
        return surrogateAddress;
    }

    public void setSurrogateAddress(String surrogateAddress) {
        this.surrogateAddress = surrogateAddress;
    }

    public String getSurrogateCity() {
        return surrogateCity;
    }

    public void setSurrogateCity(String surrogateCity) {
        this.surrogateCity = surrogateCity;
    }

    public String getSurrogateState() {
        return surrogateState;
    }

    public void setSurrogateState(String surrogateState) {
        this.surrogateState = surrogateState;
    }

    public String getSurrogatePincode() {
        return surrogatePincode;
    }

    public void setSurrogatePincode(String surrogatePincode) {
        this.surrogatePincode = surrogatePincode;
    }

    public Surrogate(String surrogateName, String surrogateRelation, String surrogatePhone,
                     String surrogateAddress, String surrogateCity, String surrogateState, String surrogatePincode) {
        this.surrogateName = surrogateName;
        this.surrogateRelation = surrogateRelation;
        this.surrogatePhone = surrogatePhone;
        this.surrogateAddress = surrogateAddress;
        this.surrogateCity = surrogateCity;
        this.surrogateState = surrogateState;
        this.surrogatePincode = surrogatePincode;
    }

    public Surrogate() {
    }
}