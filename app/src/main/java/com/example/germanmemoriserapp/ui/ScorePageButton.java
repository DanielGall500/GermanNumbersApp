package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.LoadScoresScreen;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;
import com.example.germanmemoriserapp.activity_managers.ScoresActivityManager;

public class ScorePageButton extends GeneralButton {

    private Class loadScreen = LoadScoresScreen.class;

    private NextActivityManager scoresActivityManager;

    private String loadScoreKey;
    private Context appContext;
    private AppCompatActivity appActivity;

    private int loadId;

    public ScorePageButton(Context context, AppCompatActivity activity,
                           int resId, int loadId) {
        super(context, activity, resId);

        this.appContext = context;
        this.appActivity = activity;
        this.loadId = loadId;

        scoresActivityManager = new ScoresActivityManager(
                getContext(), getActivity());

        this.loadScoreKey = activity.getString(
                R.string.score_load_intent_key);
        scoresActivityManager.addInformation(loadScoreKey, loadId);
    }

    @Override
    public void onAnimEnd() {
        scoresActivityManager.run();
    }
}
