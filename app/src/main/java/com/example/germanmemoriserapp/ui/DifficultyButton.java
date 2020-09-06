package com.example.germanmemoriserapp.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.germanmemoriserapp.mechanics.Difficulty;

public class DifficultyButton {

    ImageButton diffBtn;
    AppCompatActivity appActivity;
    int resourcePressed;

    Drawable pressedImg;
    Drawable unpressedImg;

    boolean isPressed = false;

    Difficulty currDifficulty;

    public void DifficultyButton(AppCompatActivity activity, int btnId, int resUnpressed, int resPressed, int difficultyId, boolean isPressed) {
        diffBtn = activity.findViewById(btnId);
        appActivity = activity;
        resourcePressed = resPressed;
        this.isPressed = isPressed;

        currDifficulty = new Difficulty(difficultyId);

        pressedImg = ResourcesCompat.getDrawable(appActivity.getResources(), resPressed, null);
        unpressedImg = ResourcesCompat.getDrawable(appActivity.getResources(), resUnpressed, null);
    }

    public void flipState() {
        setState(!isPressed);
    }

    public int getDifficultyId() {
        return currDifficulty.getId();
    }

    private void setState(boolean isPressed) {
        Drawable nextImg = isPressed ? pressedImg : unpressedImg;
        diffBtn.setBackground(nextImg);
    }


}
