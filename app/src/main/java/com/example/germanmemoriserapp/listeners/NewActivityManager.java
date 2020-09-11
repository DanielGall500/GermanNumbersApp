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

    Context currContext;
    AppCompatActivity currActivity;
    Class moveTo;
    boolean isGame;
    int info;

    Intent moveToNewScreen;

    public NewActivityManager() {}

    public NewActivityManager(Context context, AppCompatActivity activity,
                              Class newActivity) {
        this.currContext = context;
        this.currActivity = activity;
        this.moveTo = newActivity;
        this.isGame = false;
    }

    public NewActivityManager(Context context, AppCompatActivity activity,
                              boolean isGame, int info) {
        this.currContext = context;
        this.currActivity = activity;
        this.isGame = isGame;
        this.info = info;
    }

    public void move() {
        if(isGame) {
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

            currContext.startActivity(moveToNewScreen);
            currActivity.finish();
        }
        else {
            moveToNewScreen = new Intent(currActivity, moveTo);

            currContext.startActivity(moveToNewScreen);
            currActivity.finish();
        }
    }




    public void move(Context currContext, AppCompatActivity currActivity,
                                     Class newActivity) {
        this.currContext = currContext;
        this.currActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);

        currContext.startActivity(moveToNewScreen);
        currActivity.finish();
    }

    public void move(Context currContext, AppCompatActivity currActivity,
                     Class newActivity, int request, String key) {
        this.currContext = currContext;
        this.currActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);
        moveToNewScreen.putExtra(key, request);

        currContext.startActivity(moveToNewScreen);
        currActivity.finish();
    }

    /*
    Used for moving to a loading screen
     */
    public void move(Context currContext, AppCompatActivity currActivity,
                                     boolean isGame, int info) {
        this.currContext = currContext;
        this.currActivity = currActivity;

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

        currContext.startActivity(moveToNewScreen);
        currActivity.finish();

    }

}
