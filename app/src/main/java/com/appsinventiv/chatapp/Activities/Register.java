package com.appsinventiv.chatapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appsinventiv.chatapp.NetworkManager.AppConfig;
import com.appsinventiv.chatapp.NetworkManager.UserClient;
import com.appsinventiv.chatapp.NetworkResponses.RegisterResponse;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;
import com.appsinventiv.chatapp.Utils.SharedPrefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText city, name, email, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().length() == 0) {
                    name.setError("Enter name");
                } else if (email.getText().length() == 0) {
                    email.setError("Enter email");
                } else if (password.getText().length() == 0) {
                    password.setError("Enter password");
                } else if (city.getText().length() == 0) {
                    city.setError("Enter city");
                } else {
                    registerUser();
                }
            }
        });

    }

    private void registerUser() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("name", name.getText().toString());
        map.addProperty("email", email.getText().toString());
        map.addProperty("password", password.getText().toString());
        map.addProperty("fcmKey", FirebaseInstanceId.getInstance().getToken());
        map.addProperty("city", city.getText().toString());
        map.addProperty("emailVerified", "yes");
        map.addProperty("picUrl", "");

        Call<RegisterResponse> call = getResponse.registerUser(map);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {

                    RegisterResponse object = response.body();
                    if (object != null) {
                        if (object.getError().getMessage().equalsIgnoreCase("false")) {
                            SharedPrefs.setUserModel(object.getUser());
                            CommonUtils.showToast("Successfully registered");
                            finish();
                        } else if (object.getError().getMessage().equalsIgnoreCase("email already exist")) {
                            CommonUtils.showToast("Email Already Exists");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });

    }
}
