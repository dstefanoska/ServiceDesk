package com.daka.sdk.models;

import java.util.List;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Task {

    int id;
    String title;
    String notes;
    Building building;
    Elevator elevator;
    List<Servicer> servicers;
    int numberOfAssignees;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public List<Servicer> getServicers() {
        return servicers;
    }

    public void setServicers(List<Servicer> servicers) {
        this.servicers = servicers;
    }

    public int getNumberOfAssignees() {
        return numberOfAssignees;
    }

    public void setNumberOfAssignees(int numberOfAssignees) {
        this.numberOfAssignees = numberOfAssignees;
    }
}
