package com.example.booklibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username_text, reTypeP_text, password_text;
    MyDatabaseHelper myDB;
    Button register_button, go_to_login_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDB = new MyDatabaseHelper(this);

        username_text = findViewById(R.id.userName_text);
        password_text = findViewById(R.id.passWord_text);
        reTypeP_text = findViewById(R.id.retypeP_text);
        register_button = findViewById(R.id.register_finish_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking none of the fields are empty
                String user, pass, rpass;
                user = username_text.getText().toString();
                pass = password_text.getText().toString();
                rpass = reTypeP_text.getText().toString();

                if(user.isEmpty() || pass.isEmpty() || rpass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(rpass)){
                        if(myDB.checkUserUnique(user)){
                            Toast.makeText(RegisterActivity.this, "Username is already in use", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        myDB.addUser(user, pass);


                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Passwords did not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
