package com.appsinventiv.chatapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageModel {
    @SerializedName("messageText")
    @Expose
    private String messageText;
    @SerializedName("messageType")
    @Expose
    private String messageType;
    @SerializedName("messageByName")
    @Expose
    private String messageByName;
    @SerializedName("messageById")
    @Expose
    private Integer messageById;
    @SerializedName("roomId")
    @Expose
    private Long roomId;
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageByName() {
        return messageByName;
    }

    public void setMessageByName(String messageByName) {
        this.messageByName = messageByName;
    }

    public Integer getMessageById() {
        return messageById;
    }

    public void setMessageById(Integer messageById) {
        this.messageById = messageById;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
