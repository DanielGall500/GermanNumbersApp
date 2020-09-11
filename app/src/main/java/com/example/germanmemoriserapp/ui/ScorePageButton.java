package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.LoadScoresScreen;
import com.example.germanmemoriserapp.listeners.NewActivityManager;

public class ScorePageButton extends GeneralButton {

    private Class loadScreen = LoadScoresScreen.class;

    private NewActivityManager moveToScreen = new NewActivityManager();
    private String loadScoreKey;
    private Context appContext;
    private AppCompatActivity appActivity;

    private int loadId;

    public ScorePageButton(int resId, Context context,
                           AppCompatActivity activity, int loadId) {
        super(resId, context, activity);

        this.appContext = context;
        this.appActivity = activity;
        this.loadId = loadId;

        this.loadScoreKey = activity.getString(
                R.string.score_load_intent_key);
    }

    @Override
    public void onAnimEnd() {
        moveToScreen.move(appContext, appActivity,loadScreen,
                loadId, loadScoreKey);
    }
}
