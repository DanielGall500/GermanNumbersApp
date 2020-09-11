package com.example.germanmemoriserapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.LoadAudioScreen;
import com.example.germanmemoriserapp.mechanics.Difficulty;

public class PlayActivityManager extends NextActivityManager {

    private Class loadScreen = LoadAudioScreen.class;

    private String isGameKey;
    private String loadInfo;

    private int difficultyId;

    public PlayActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(loadScreen);

        isGameKey = getContext().getString(
                R.string.load_screen_isGameBoolean);

        loadInfo = getContext().getString(
                R.string.load_screen_information);
    }

    public void setDifficulty(int diffId) {
        this.difficultyId = diffId;
    }

    @Override
    public void run() {
        addInformation(isGameKey, true);
        addInformation(loadInfo, difficultyId);

        System.out.println("Setting Difficulty: " + difficultyId);

        super.run();
    }




}
