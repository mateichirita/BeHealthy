package com.example.matei.behealthy.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matei.behealthy.R;
import com.example.matei.behealthy.utility.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    public final static String usersFile = "users.bin";
    private EditText usernameEditText = null;
    private EditText passwordEditText = null;
    private EditText repeatPasswordEditText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.registerUserEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
    }

    public void RegisterUser(View view) {
        if(Validate()) {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            User user = new User(username, password);
            try {
                FileOutputStream fileOutputStream =
                        openFileOutput(usersFile, MODE_PRIVATE);
                if(MainActivity.userList == null) {
                    MainActivity.userList = new ArrayList<>();
                }
                MainActivity.userList.add(user);
                ObjectOutputStream stream = new ObjectOutputStream(fileOutputStream);
                stream.writeObject(MainActivity.userList);
                stream.close();
                fileOutputStream.close();
                finish();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            MenuActivity.currentUser = user;
            Intent intent =
                    new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean Validate() {
        boolean incorrectUser =
                usernameEditText.getText().toString().trim().length() <= 4;
        boolean incorrectPassword =
                passwordEditText.getText().toString().trim().length() <= 4;
        if(incorrectUser || incorrectPassword) {
            Toast.makeText(getApplicationContext(), "Incorrect login",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        boolean passswordsMatch = passwordEditText.getText().toString().
                equals(repeatPasswordEditText.getText().toString());
        if(!passswordsMatch) {
            Toast.makeText(getApplicationContext(), "Passwords don't match!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
