package com.example.epravara;

public class Contacts {

    String contactName, contactRelation, contactNumber;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Contacts() {
    }

    public Contacts(String contactName, String contactRelation, String contactNumber) {
        this.contactName = contactName;
        this.contactRelation = contactRelation;
        this.contactNumber = contactNumber;
    }
}
