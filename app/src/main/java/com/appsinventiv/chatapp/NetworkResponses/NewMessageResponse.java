
package com.appsinventiv.chatapp.NetworkResponses;

import com.appsinventiv.chatapp.Model.MessageModel;
import com.appsinventiv.chatapp.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewMessageResponse {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("messages")
    @Expose
    private List<MessageModel> messages = null;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }
}
