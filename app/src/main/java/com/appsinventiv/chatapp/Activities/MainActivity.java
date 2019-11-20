package com.appsinventiv.chatapp.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsinventiv.chatapp.Adapters.ChatListAdapter;
import com.appsinventiv.chatapp.Model.MessageModel;
import com.appsinventiv.chatapp.Model.UserMessages;
import com.appsinventiv.chatapp.Model.UserModel;
import com.appsinventiv.chatapp.NetworkManager.AppConfig;
import com.appsinventiv.chatapp.NetworkManager.UserClient;
import com.appsinventiv.chatapp.NetworkResponses.AllRoomMessagesResponse;
import com.appsinventiv.chatapp.NetworkResponses.UpdateFcmResponse;
import com.appsinventiv.chatapp.NetworkResponses.UserMessagesResponse;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;
import com.appsinventiv.chatapp.Utils.SharedPrefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newMessage;
    RecyclerView recycler;

    ChatListAdapter adapter;
    private List<UserMessages> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newMessage = findViewById(R.id.newMessage);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ChatListAdapter(this, itemList);
        recycler.setAdapter(adapter);

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactSelectionScreen.class));
            }
        });
        updateFcmKey();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessagesFromDB();
    }

    private void getMessagesFromDB() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("id", SharedPrefs.getUserModel().getId());
        Call<UserMessagesResponse> call = getResponse.userMessages(map);
        call.enqueue(new Callback<UserMessagesResponse>() {
            @Override
            public void onResponse(Call<UserMessagesResponse> call, Response<UserMessagesResponse> response) {
                if (response.isSuccessful()) {

                    UserMessagesResponse object = response.body();
                    if (object != null) {
                        itemList.clear();
                        if (object.getMessages() != null && object.getMessages().size() > 0) {
                            itemList = object.getMessages();
                        }
                        adapter.setItemList(itemList);
                    }

                }
            }

            @Override
            public void onFailure(Call<UserMessagesResponse> call, Throwable t) {
                CommonUtils.showToast(t.getMessage());
            }
        });
    }

    private void updateFcmKey() {

        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("id", SharedPrefs.getUserModel().getId());
        map.addProperty("fcmKey", FirebaseInstanceId.getInstance().getToken());
        Call<UpdateFcmResponse> call = getResponse.updateFcmKey(map);
        call.enqueue(new Callback<UpdateFcmResponse>() {
            @Override
            public void onResponse(Call<UpdateFcmResponse> call, Response<UpdateFcmResponse> response) {
                if (response.isSuccessful()) {

                    UpdateFcmResponse object = response.body();
                    if (object != null) {
                        UserModel user = object.getUser();
                        SharedPrefs.setUserModel(user);
                    }

                }
            }

            @Override
            public void onFailure(Call<UpdateFcmResponse> call, Throwable t) {

            }
        });
    }

}
