package com.daka.sdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Task {

    @SerializedName("taskId")
    int id;
    int elevatorId;
    String dueDate;
    String title;
    String notes;
    List<Assignee> assignees;
    int numberOfAssignees;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    public int getNumberOfAssignees() {
        return numberOfAssignees;
    }

    public void setNumberOfAssignees(int numberOfAssignees) {
        this.numberOfAssignees = numberOfAssignees;
    }
}
