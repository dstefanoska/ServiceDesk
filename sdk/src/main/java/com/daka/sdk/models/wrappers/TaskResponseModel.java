package com.daka.sdk.models.wrappers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dana on 30-Sep-17.
 */

public class TaskResponseModel {
    int assignationId;
    int taskId;
    @SerializedName("servicePersonId")
    String servicerId ;
    int taskStateId;
    int commentId;

    public TaskResponseModel() {
    }

    public int getAssignationId() {
        return assignationId;
    }

    public void setAssignationId(int assignationId) {
        this.assignationId = assignationId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getServicerId() {
        return servicerId;
    }

    public void setServicerId(String servicerId) {
        this.servicerId = servicerId;
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
