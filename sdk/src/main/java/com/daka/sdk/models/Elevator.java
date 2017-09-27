package com.daka.sdk.models;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Elevator {
    int id;
    int code;
    String inverterModel;
    String machine;
    int motorKw;
    int motorAmp;
    boolean display;
    boolean doors;
    String installationDate;
    String tableType;
//    int buildingId;
//    String buildingName;

    public Elevator() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInverterModel() {
        return inverterModel;
    }

    public void setInverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getMotorKw() {
        return motorKw;
    }

    public void setMotorKw(int motorKw) {
        this.motorKw = motorKw;
    }

    public int getMotorAmp() {
        return motorAmp;
    }

    public void setMotorAmp(int motorAmp) {
        this.motorAmp = motorAmp;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isDoors() {
        return doors;
    }

    public void setDoors(boolean doors) {
        this.doors = doors;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
}
