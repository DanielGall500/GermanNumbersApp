package com.example.germanmemoriserapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activities.menu.MenuScreen;

public class MenuActivityManager extends NextActivityManager {
    private Class menuScreen = MenuScreen.class;

    public MenuActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(menuScreen);
    }
}
