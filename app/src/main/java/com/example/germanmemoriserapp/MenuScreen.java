package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.germanmemoriserapp.listeners.ExitAppListener;
import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;

import java.util.ArrayList;

public class MenuScreen extends AppCompatActivity {

    private Button playGameBtn, exitGameBtn;
    private Spinner levelSpinner;

    private ArrayList<DifficultyItem> difficulties;
    private DifficultyAdapter difficultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        playGameBtn = findViewById(R.id.playGameBtn);
        exitGameBtn = findViewById(R.id.exitGameBtn);
        levelSpinner = findViewById(R.id.difficultySpinner);

        playGameBtn.setOnClickListener(new MoveToNewActivityListener(
                this,this, MainActivity.class));

        exitGameBtn.setOnClickListener(new ExitAppListener(this));

        createDifficultyList();
        difficultyAdapter = new DifficultyAdapter(this, difficulties);

        levelSpinner.setAdapter(difficultyAdapter);
    }

    private void createDifficultyList() {
        difficulties = new ArrayList<>();

        DifficultyItem beginner = new DifficultyItem(getString(R.string.difficulty_one),0);

        difficulties.add(beginner);
    }
}



















