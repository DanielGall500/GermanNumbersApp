package com.example.germanmemoriserapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activities.learn.LearnScreen;

public class LearnActivityManager extends NextActivityManager {
    private Class learnScreen = LearnScreen.class;

    public LearnActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(learnScreen);
    }
}
