package com.appsinventiv.chatapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appsinventiv.chatapp.Model.UserModel;
import com.appsinventiv.chatapp.NetworkManager.AppConfig;
import com.appsinventiv.chatapp.NetworkManager.UserClient;
import com.appsinventiv.chatapp.NetworkResponses.LoginResponse;
import com.appsinventiv.chatapp.NetworkResponses.RegisterResponse;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;
import com.appsinventiv.chatapp.Utils.PrefManager;
import com.appsinventiv.chatapp.Utils.SharedPrefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;

    RelativeLayout wholeLayout;
    TextView register;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        register = findViewById(R.id.register);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        wholeLayout = findViewById(R.id.wholeLayout);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(Login.this,MainActivity.class));
                if (email.getText().length() == 0) {
                    email.setError("Enter email");
                } else if (password.getText().length() == 0) {
                    password.setError("Enter password");
                } else {

                    loginUser();
                }
            }
        });


    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Login.this, MainActivity.class));
        prefManager.setIsFirstTimeLaunchWelcome(false);
        finish();
    }


    private void loginUser() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);

        JsonObject map = new JsonObject();
        map.addProperty("email", email.getText().toString());
        map.addProperty("password", password.getText().toString());
        map.addProperty("fcmKey", FirebaseInstanceId.getInstance().getToken());

        Call<LoginResponse> call = getResponse.loginUser(map);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    LoginResponse object = response.body();
                    if (object != null) {
                        if (object.getError().getMessage().equalsIgnoreCase("false")) {
                            if (object.getUser().getEmailVerified().equalsIgnoreCase("no")) {
                                CommonUtils.showToast("Email is not verified");
                            } else {
                                CommonUtils.showToast("Successfully logged in");
                                SharedPrefs.setUserModel(object.getUser());
                                launchHomeScreen();
                            }
                        } else if (object.getError().getMessage().equalsIgnoreCase("Wrong email or password")) {
                            CommonUtils.showToast("Wrong email or password");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
