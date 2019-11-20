
package com.appsinventiv.chatapp.NetworkResponses;

import com.appsinventiv.chatapp.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFcmResponse {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("user")
    @Expose
    private UserModel user;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
