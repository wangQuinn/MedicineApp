package com.example.booklibraryapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    Button login_button, register_button;
    EditText username, pwd;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.cream)); //setting the background indivdiually here because it uses a different theme than the rest of the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDB = new MyDatabaseHelper(this);

        username = findViewById(R.id.userName_l);
        pwd = findViewById(R.id.passWord_l);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        EditText edittext = (EditText)findViewById(R.id.passWord_l);
        edittext.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLoggedId = myDB.checkUser(username.getText().toString().trim(), pwd.getText().toString().trim());
                if(isLoggedId){

                    //Save the logged-in user's ID
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USER_ID", username.getText().toString());
                    editor.apply();

                    //changes screen to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(LoginActivity.this, "Shiver me timber, aw shuckers you can't login! Double Check :3", Toast.LENGTH_LONG).show();
                }
            }
        });



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //go to register page
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



    }

    public String getLoggedInUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        return sharedPreferences.getString("userId", null);
    }}


;
