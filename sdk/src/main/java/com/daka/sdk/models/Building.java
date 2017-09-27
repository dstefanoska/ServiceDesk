package com.daka.sdk.models;

import java.util.List;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Building {

    int id;
    String name;
    int locationId;
    String locationName;
    String address;
    double longitude;
    double latitude;
    String notes;
    int accountNumber;
    String agreementStartDate;
    String agreementEndDate;
    boolean active;
    String comment;
    String contractMethod;
    List<Elevator> elevators;

    public Building() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgreementStartDate() {
        return agreementStartDate;
    }

    public void setAgreementStartDate(String agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
    }

    public String getAgreementEndDate() {
        return agreementEndDate;
    }

    public void setAgreementEndDate(String agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContractMethod() {
        return contractMethod;
    }

    public void setContractMethod(String contractMethod) {
        this.contractMethod = contractMethod;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }
}
