package com.appsinventiv.chatapp.NetworkManager;


import com.appsinventiv.chatapp.NetworkResponses.AllRoomMessagesResponse;
import com.appsinventiv.chatapp.NetworkResponses.CreateRoomResponse;
import com.appsinventiv.chatapp.NetworkResponses.LoginResponse;
import com.appsinventiv.chatapp.NetworkResponses.NewMessageResponse;
import com.appsinventiv.chatapp.NetworkResponses.RegisterResponse;
import com.appsinventiv.chatapp.NetworkResponses.UpdateFcmResponse;
import com.appsinventiv.chatapp.NetworkResponses.UserListResponse;
import com.appsinventiv.chatapp.NetworkResponses.UserMessagesResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserClient {

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/user/register")
    Call<RegisterResponse> registerUser(
            @Body JsonObject jsonObject

    );

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/user/login")
    Call<LoginResponse> loginUser(
            @Body JsonObject jsonObject

    );

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/user/updateFcmKey")
    Call<UpdateFcmResponse> updateFcmKey(
            @Body JsonObject jsonObject

    );

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/room/createRoom")
    Call<CreateRoomResponse> createRoom(
            @Body JsonObject jsonObject

    );

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/message/createMessage")
    Call<NewMessageResponse> createMessage(
            @Body JsonObject jsonObject

    );


    @Headers("Content-Type: application/json")
    @POST("chat/public/api/allUsers")
    Call<UserListResponse> allUsers(   @Body JsonObject jsonObject);


    @Headers("Content-Type: application/json")
    @POST("chat/public/api/message/allRoomMessages")
    Call<AllRoomMessagesResponse> allRoomMessages(
            @Body JsonObject jsonObject

    );

    @Headers("Content-Type: application/json")
    @POST("chat/public/api/message/userMessages")
    Call<UserMessagesResponse> userMessages(
            @Body JsonObject jsonObject

    );
}
