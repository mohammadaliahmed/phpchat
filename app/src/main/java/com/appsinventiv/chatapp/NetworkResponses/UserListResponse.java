
package com.appsinventiv.chatapp.NetworkResponses;

import com.appsinventiv.chatapp.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("user")
    @Expose
    private List<UserModel> user = null;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<UserModel> getUser() {
        return user;
    }

    public void setUser(List<UserModel> user) {
        this.user = user;
    }
}
