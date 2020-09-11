package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activity_managers.ScoresActivityManager;

public class ScoresMenuButton extends GeneralButton {
    ScoresActivityManager scoresActivityManager;

    public ScoresMenuButton(Context context, AppCompatActivity activity,
                           int resId) {
        super(context, activity, resId);
        scoresActivityManager = new ScoresActivityManager(context,activity);
    }

    @Override
    protected void onAnimEnd() {
        scoresActivityManager.run();
    }
}
