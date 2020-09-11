package com.example.germanmemoriserapp.ui.buttons;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;

public class BackButton {

    NextActivityManager activityListener;
    private Context appContext;
    private AppCompatActivity appActivity;
    private Class moveToActivity;
    private ImageButton backButton;
    private Animation backBtnAnimation;

    public BackButton(Context appContext, AppCompatActivity appActivity, int buttonId, Class moveTo) {
        this.appContext = appContext;
        this.appActivity = appActivity;
        this.moveToActivity = moveTo;

        activityListener = new NextActivityManager(appContext, appActivity);
        activityListener.setNextActivity(moveTo);

        this.backButton = appActivity.findViewById(buttonId);
        this.backButton.setOnClickListener(new BackButtonListener());

        backBtnAnimation = AnimationUtils.loadAnimation(appContext, R.anim.fly_left);
        backBtnAnimation.setAnimationListener(new BackButtonAnimationListener());
    }


    private class BackButtonAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            backButton.setVisibility(View.INVISIBLE);
            activityListener.run();
        }
    }


    private class BackButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            backButton.startAnimation(backBtnAnimation);
        }
    }


}
