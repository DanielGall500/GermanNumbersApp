package com.example.germanmemoriserapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activities.ScoreBoardScreen;

public class ScoresActivityManager extends NextActivityManager {

    private Class nextActivity = ScoreBoardScreen.class;

    public ScoresActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(nextActivity);
    }
}
