package com.daka.sdk.models;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Sos {

    int sosId;
    int locationId;
    String buildingSupervisor;
    String phoneNumber;

    public Sos() {
    }

    public int getSosId() {
        return sosId;
    }

    public void setSosId(int sosId) {
        this.sosId = sosId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getBuildingSupervisor() {
        return buildingSupervisor;
    }

    public void setBuildingSupervisor(String buildingSupervisor) {
        this.buildingSupervisor = buildingSupervisor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
