package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.germanmemoriserapp.listeners.ExitAppListener;
import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;

public class MenuScreen extends AppCompatActivity {

    Button playGameBtn, exitGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        playGameBtn = findViewById(R.id.playGameBtn);
        exitGameBtn = findViewById(R.id.exitGameBtn);

        playGameBtn.setOnClickListener(new MoveToNewActivityListener(
                this,this, MainActivity.class));

        exitGameBtn.setOnClickListener(new ExitAppListener(this));
    }
}
