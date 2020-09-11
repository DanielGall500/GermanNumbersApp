package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.LoadAudioScreen;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;

public class RetryButton extends GeneralButton {

    private int difficulty;
    private Context appContext;
    private AppCompatActivity appActivity;

    public RetryButton(Context context, AppCompatActivity activity,
                       int resId, int diffId) {
        super(context, activity, resId);
        this.appContext = context;
        this.appActivity = activity;
        this.difficulty = diffId;
    }

    @Override
    protected void onAnimEnd() {

        final String isGameKey = appActivity.getString(
                R.string.load_screen_isGameBoolean);

        final String infoKey = appActivity.getString(
                R.string.load_screen_information);

        NextActivityManager nextActivityManager = new NextActivityManager(
                appContext,appActivity);

        nextActivityManager.setNextActivity(LoadAudioScreen.class);
        nextActivityManager.addInformation(isGameKey, true);
        nextActivityManager.addInformation(infoKey, difficulty);
        nextActivityManager.run();

    }
}
