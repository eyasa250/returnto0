package com.example.returnto0.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("roomName")
    private String roomName;
    @SerializedName("taskId")
    private String taskId;

    public Event(String id, String roomName, String taskId, Date endTime) {
        this.id = id;
        this.roomName = roomName;
        this.taskId = taskId;
        this.endTime = endTime;
    }

    @SerializedName("endTime")
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
