package com.appsinventiv.chatapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.appsinventiv.chatapp.Adapters.UsersListAdapter;
import com.appsinventiv.chatapp.Model.Room;
import com.appsinventiv.chatapp.Model.UserModel;
import com.appsinventiv.chatapp.NetworkManager.AppConfig;
import com.appsinventiv.chatapp.NetworkManager.UserClient;
import com.appsinventiv.chatapp.NetworkResponses.CreateRoomResponse;
import com.appsinventiv.chatapp.NetworkResponses.LoginResponse;
import com.appsinventiv.chatapp.NetworkResponses.UserListResponse;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.PrefManager;
import com.appsinventiv.chatapp.Utils.SharedPrefs;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactSelectionScreen extends AppCompatActivity {


    RecyclerView recycler;
    UsersListAdapter adapter;
    private ArrayList<UserModel> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_selection);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.setTitle("Select contact");


        recycler = findViewById(R.id.recycler);
        adapter = new UsersListAdapter(this, itemList, new UsersListAdapter.UserListAdapterCallbacks() {
            @Override
            public void onUserSelected(UserModel model) {
                createRoomInDb(model);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        getUsersFromDB();


    }

    private void createRoomInDb(final UserModel model) {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
        JsonObject map = new JsonObject();
        map.addProperty("userIds", SharedPrefs.getUserModel().getId() + "," + model.getId());

        Call<CreateRoomResponse> call = getResponse.createRoom(map);
        call.enqueue(new Callback<CreateRoomResponse>() {
            @Override
            public void onResponse(Call<CreateRoomResponse> call, Response<CreateRoomResponse> response) {
                if (response.isSuccessful()) {
                    CreateRoomResponse object = response.body();
                    if (object.getError().getMessage().equalsIgnoreCase("Room Already Exists")) {
                        Room room = response.body().getRoom();
                        Intent i = new Intent(ContactSelectionScreen.this, ChattingScreen.class);
                        i.putExtra("roomId", room.getId());
                        i.putExtra("name", model.getName());

                        startActivity(i);
                    } else {
                        Room room = response.body().getRoom();
                        Intent i = new Intent(ContactSelectionScreen.this, ChattingScreen.class);
                        i.putExtra("roomId", room.getId());
                        i.putExtra("name", model.getName());
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateRoomResponse> call, Throwable t) {

            }
        });


    }

    private void getUsersFromDB() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
        JsonObject map = new JsonObject();
        map.addProperty("city", SharedPrefs.getUserModel().getCity());
        Call<UserListResponse> call = getResponse.allUsers(map);
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful()) {
                    UserListResponse object = response.body();
                    if (object != null) {
                        if (object.getError().getMessage().equalsIgnoreCase("false")) {
                            if (object.getUser().size() > 0) {
                                itemList.clear();
                                HashMap<Integer, UserModel> modelHashMap = new HashMap<>();
                                for (UserModel userModel : object.getUser()) {
                                    if (!userModel.getEmail().equalsIgnoreCase(SharedPrefs.getUserModel().getEmail())) {
                                        itemList.add(userModel);
                                        modelHashMap.put(userModel.getId(), userModel);
                                    }
                                }
                                SharedPrefs.setUserList(modelHashMap);
                                adapter.setItemList(itemList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {

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
}
