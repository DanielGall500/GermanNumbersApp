package com.example.germanmemoriserapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.activity_managers.PlayActivityManager;

public class PlayMenuButton extends GeneralButton {

    PlayActivityManager playActivityManager;
    private int currDiffId;

    public PlayMenuButton(Context context, AppCompatActivity activity,
                          int resId, int currDifficultyId) {
        super(context, activity, resId);

        this.currDiffId = currDifficultyId;
        playActivityManager = new PlayActivityManager(getContext(), getActivity());
    }

    public int getDifficulty() {
        return this.currDiffId;
    }

    public void setDifficulty(int diffId) {
        this.currDiffId = diffId;
    }

    @Override
    protected void onAnimEnd() {
        playActivityManager.setDifficulty(getDifficulty());
        playActivityManager.run();
    }
}
