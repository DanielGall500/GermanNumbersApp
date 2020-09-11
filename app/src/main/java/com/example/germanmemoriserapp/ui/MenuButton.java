package com.example.germanmemoriserapp.ui;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.listeners.NewActivityManager;

public class MenuButton extends GeneralButton {

    public enum TYPE {
        PLAY, LEARN, SCORES
    }

    private TYPE btnType;
    private NewActivityManager moveToActivity;

    public MenuButton(TYPE type, int resId, Context context,
                      AppCompatActivity activity, NewActivityManager btnNewActivityManager) {
        super(resId,context,activity);
        this.btnType = type;
        this.moveToActivity = btnNewActivityManager;
    }

    @Override
    protected void onAnimEnd() {
        moveToActivity.move();
    }

}
