package com.daka.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dana on 23-Sep-17.
 */

public class Assignee {
    @SerializedName("AssignationId")
    int id;
    int taskId;
    String servicePersonId;
    int taskStateId;
    int commentId;

    public Assignee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getServicePersonId() {
        return servicePersonId;
    }

    public void setServicePersonId(String servicePersonId) {
        this.servicePersonId = servicePersonId;
    }

    public int getTaskStateId() {
        return taskStateId;
    }

    public void setTaskStateId(int taskStateId) {
        this.taskStateId = taskStateId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
