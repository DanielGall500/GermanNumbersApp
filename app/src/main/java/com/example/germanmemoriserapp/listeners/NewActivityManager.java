package com.example.germanmemoriserapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.GameScreen;
import com.example.germanmemoriserapp.activities.LoadAudioScreen;

public class NewActivityManager {

    Context currentContext;
    AppCompatActivity currentActivity;

    Intent moveToNewScreen;

    public NewActivityManager() {}

    public void setLoadRequest(int id) {

    }

    public void move(Context currContext, AppCompatActivity currActivity,
                                     Class newActivity) {
        this.currentContext = currContext;
        this.currentActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);

        currentContext.startActivity(moveToNewScreen);
        currentActivity.finish();
    }

    public void move(Context currContext, AppCompatActivity currActivity,
                     Class newActivity, int request, String key) {
        this.currentContext = currContext;
        this.currentActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);
        moveToNewScreen.putExtra(key, request);

        currentContext.startActivity(moveToNewScreen);
        currentActivity.finish();
    }

    /*
    Used for moving to a loading screen
     */
    public void move(Context currContext, AppCompatActivity currActivity,
                                     boolean isGame, int info) {
        this.currentContext = currContext;
        this.currentActivity = currActivity;

        moveToNewScreen = new Intent(
                currActivity, LoadAudioScreen.class);

        String isGameKey = currContext.getString(
                R.string.load_screen_isGameBoolean);
        String loadInfo = currContext.getString(
                R.string.load_screen_information);

        /*
        Pass relevant information to loading screen.
         */
        moveToNewScreen.putExtra(isGameKey, isGame);
        moveToNewScreen.putExtra(loadInfo, info);

        currentContext.startActivity(moveToNewScreen);
        currentActivity.finish();

    }

}
