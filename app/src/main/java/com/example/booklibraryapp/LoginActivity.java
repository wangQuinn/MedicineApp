package com.example.booklibraryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button login_button, register_button;
    EditText username, pwd;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.cream));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new MyDatabaseHelper(this);

        username = findViewById(R.id.userName_l);
        pwd = findViewById(R.id.passWord_l);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        EditText edittext = findViewById(R.id.passWord_l);
        edittext.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = username.getText().toString().trim();
                String passInput = pwd.getText().toString().trim();

                // Retrieve stored hash and salt from the database
                String[] userData = myDB.getUserCredentials(userInput);
                if (userData == null) {
                    Toast.makeText(LoginActivity.this, "Username not found!", Toast.LENGTH_LONG).show();
                    return;
                }

                String storedHash = userData[0];
                String storedSalt = userData[1];

                // Verify password
                if (PasswordHashing.verifyPassword(passInput, storedHash, storedSalt)) {
                    // Save the logged-in user's ID
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USER_ID", userInput);
                    editor.apply();

                    // Move to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Shiver me timber, aw shuckers you can't login! Double Check :3", Toast.LENGTH_LONG).show();
                }
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        return sharedPreferences.getString("USER_ID", null);
    }
}
