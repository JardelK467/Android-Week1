package com.example.week1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button caButton;//Create account button initialised

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caButton = findViewById(R.id.create_button);//Assigns button id
        caButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateAccount.class);
            startActivity(intent);
        });
    }
}