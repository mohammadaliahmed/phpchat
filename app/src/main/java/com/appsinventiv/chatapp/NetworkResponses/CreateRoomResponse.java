
package com.appsinventiv.chatapp.NetworkResponses;

import com.appsinventiv.chatapp.Model.Room;
import com.appsinventiv.chatapp.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRoomResponse {


    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("room")
    @Expose
    private Room room;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
