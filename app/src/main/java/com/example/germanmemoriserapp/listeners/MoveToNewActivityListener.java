package com.example.germanmemoriserapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;

public class MoveToNewActivityListener implements View.OnClickListener {

    Context currentContext;
    AppCompatActivity currentActivity;

    Intent moveToNewScreen;

    Animation btnAnimation;

    public MoveToNewActivityListener(Context currContext, AppCompatActivity currActivity,
                                     Class newActivity) {
        this.currentContext = currContext;
        this.currentActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);

        btnAnimation = AnimationUtils.loadAnimation(currActivity, R.anim.button_fly);
    }

    @Override
    public void onClick(View v) {
        ImageButton btn = currentActivity.findViewById(v.getId());
        btn.startAnimation(btnAnimation);

        currentContext.startActivity(moveToNewScreen);
        currentActivity.finish();
    }
}
