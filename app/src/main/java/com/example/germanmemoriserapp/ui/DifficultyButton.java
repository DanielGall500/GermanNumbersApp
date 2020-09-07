package com.example.germanmemoriserapp.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.germanmemoriserapp.listeners.DifficultyListener;
import com.example.germanmemoriserapp.mechanics.Difficulty;

public class DifficultyButton{

    ImageButton diffBtn;
    AppCompatActivity appActivity;
    int resourcePressed;

    Drawable pressedImg;
    Drawable unpressedImg;

    boolean isPressed = false;

    Difficulty currDifficulty;

    final int btnResId;

    public DifficultyButton(AppCompatActivity activity, int btnId,
                                 int resUnpressed, int resPressed,
                                 int difficultyId, boolean isPressed) {
        diffBtn = activity.findViewById(btnId);
        appActivity = activity;
        resourcePressed = resPressed;
        this.isPressed = isPressed;
        this.btnResId = btnId;

        currDifficulty = new Difficulty(difficultyId);

        pressedImg = ResourcesCompat.getDrawable(appActivity.getResources(), resPressed, null);
        unpressedImg = ResourcesCompat.getDrawable(appActivity.getResources(), resUnpressed, null);
    }

    public void setListener(DifficultyListener l) {
        diffBtn.setOnClickListener(l);
    }

    public int getId() {
        return btnResId;
    }

    public void flipState() {
        boolean flippedState = !isPressed;

        setState(flippedState);
        this.isPressed = flippedState;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public int getDifficultyId() {
        return currDifficulty.getId();
    }

    private void setState(boolean isPressed) {
        System.out.println("Setting " + currDifficulty.getLevel().toString() + " To " + isPressed);
        Drawable nextImg = isPressed ? pressedImg : unpressedImg;
        diffBtn.setBackground(nextImg);
    }


}
