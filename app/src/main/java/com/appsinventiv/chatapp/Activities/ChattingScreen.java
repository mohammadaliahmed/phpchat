package com.appsinventiv.chatapp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.appsinventiv.chatapp.Adapters.MessagesAdapter;
import com.appsinventiv.chatapp.Model.MessageModel;
import com.appsinventiv.chatapp.NetworkManager.AppConfig;
import com.appsinventiv.chatapp.NetworkManager.UserClient;
import com.appsinventiv.chatapp.NetworkResponses.AllRoomMessagesResponse;
import com.appsinventiv.chatapp.NetworkResponses.LoginResponse;
import com.appsinventiv.chatapp.NetworkResponses.NewMessageResponse;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;
import com.appsinventiv.chatapp.Utils.Constants;
import com.appsinventiv.chatapp.Utils.PrefManager;
import com.appsinventiv.chatapp.Utils.SharedPrefs;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChattingScreen extends AppCompatActivity {


    Integer roomId;
    String name;
    EditText message;
    FloatingActionButton send;
    RecyclerView recycler;
    MessagesAdapter adapter;
    private List<MessageModel> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_screen);
        LocalBroadcastManager.getInstance(ChattingScreen.this).registerReceiver(mMessageReceiver,
                new IntentFilter("newMsg"));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        name = getIntent().getStringExtra("name");
        this.setTitle(name);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessagesAdapter(this, itemList);
        recycler.setAdapter(adapter);


        roomId = getIntent().getIntExtra("roomId", 0);
//        CommonUtils.showToast("" + roomId);

        send = findViewById(R.id.send);
        message = findViewById(R.id.message);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().length() == 0) {
                    message.setError("Cant send empty message");
                } else {
                    sendMessage();
                }
            }
        });
        getRoomMessagesFromDB();


    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            getRoomMessagesFromDB();
        }
    };

    private void getRoomMessagesFromDB() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("roomId", roomId);
        Call<AllRoomMessagesResponse> call = getResponse.allRoomMessages(map);
        call.enqueue(new Callback<AllRoomMessagesResponse>() {
            @Override
            public void onResponse(Call<AllRoomMessagesResponse> call, Response<AllRoomMessagesResponse> response) {
                if (response.isSuccessful()) {

                    AllRoomMessagesResponse object = response.body();
                    if (object.getMessages() != null && object.getMessages().size() > 0) {
                        itemList = object.getMessages();
                        Collections.sort(itemList, new Comparator<MessageModel>() {
                            @Override
                            public int compare(MessageModel listData, MessageModel t1) {
                                Long ob1 = listData.getTime();
                                Long ob2 = t1.getTime();

                                return ob1.compareTo(ob2);

                            }
                        });
                        adapter.setItemList(itemList);
                        recycler.scrollToPosition(itemList.size() - 1);

                    }
                }
            }

            @Override
            public void onFailure(Call<AllRoomMessagesResponse> call, Throwable t) {

            }
        });
    }


    private void sendMessage() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("messageText", message.getText().toString());
        map.addProperty("messageType", Constants.MESSAGE_TYPE_TEXT);
        map.addProperty("messageByName", SharedPrefs.getUserModel().getName());
        map.addProperty("messageById", SharedPrefs.getUserModel().getId());
        map.addProperty("roomId", roomId);
        map.addProperty("time", System.currentTimeMillis());
        Call<NewMessageResponse> call = getResponse.createMessage(map);
        call.enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.isSuccessful()) {
                    message.setText("");

                    NewMessageResponse object = response.body();
                    if (object.getMessages() != null && object.getMessages().size() > 0) {
                        itemList = object.getMessages();
                        Collections.sort(itemList, new Comparator<MessageModel>() {
                            @Override
                            public int compare(MessageModel listData, MessageModel t1) {
                                Long ob1 = listData.getTime();
                                Long ob2 = t1.getTime();

                                return ob1.compareTo(ob2);

                            }
                        });
                        adapter.setItemList(itemList);
                        recycler.scrollToPosition(itemList.size() - 1);

                    }
                }
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {

                CommonUtils.showToast(t.getMessage());

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {


            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LocalBroadcastManager.getInstance(ChattingScreen.this).unregisterReceiver(mMessageReceiver);

    }

}
