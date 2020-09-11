package com.example.germanmemoriserapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activity_managers.LearnActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Difficulty;

public class LearnMenuButton extends GeneralButton {

    LearnActivityManager learnActivityManager;
    private Difficulty currDiff;

    public LearnMenuButton(Context context, AppCompatActivity activity,
                           int resId) {
        super(context, activity, resId);
        learnActivityManager = new LearnActivityManager(context, activity);
    }

    public void setDifficulty(Difficulty diff) {
        this.currDiff = diff;
    }

    @Override
    protected void onAnimEnd() {
        learnActivityManager.run();
    }
}
