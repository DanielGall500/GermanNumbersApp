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

    private ArrayList<DifficultyItem> difficultyList;
    private DifficultyAdapter difficultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        playGameBtn = findViewById(R.id.playGameBtn);
        exitGameBtn = findViewById(R.id.exitGameBtn);
        levelSpinner = findViewById(R.id.difficultySpinner);

        playGameBtn.setOnClickListener(new MoveToNewActivityListener(
                this,this, LoadScreen.class));

        exitGameBtn.setOnClickListener(new ExitAppListener(this));

        difficultyList = createDifficultyList();
        difficultyAdapter = new DifficultyAdapter(this, difficultyList);

        levelSpinner.setAdapter(difficultyAdapter);
    }

    private ArrayList<DifficultyItem> createDifficultyList() {
        ArrayList<DifficultyItem> difficultyItems = new ArrayList<>();

        String[] difficultyTexts = new String[] {
                getString(R.string.difficulty_one),
                getString(R.string.difficulty_two),
                getString(R.string.difficulty_three)
        };

        DifficultyItem[] items = new DifficultyItem[] {
                new DifficultyItem(difficultyTexts[0],0),
                new DifficultyItem(difficultyTexts[1],0),
                new DifficultyItem(difficultyTexts[2],0)
        };

        for(DifficultyItem item : items)
            difficultyItems.add(item);

        return difficultyItems;
    }
}



















