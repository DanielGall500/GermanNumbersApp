package com.example.germanmemoriserapp.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.UIClip;

public class GeneralButton {

    private ImageButton btn;
    private Animation btnAnimation;
    private Context appContext;
    private AppCompatActivity appActivity;
    private SoundManager soundManager;

    public GeneralButton(int resId, Context context,
                         AppCompatActivity activity) {
        this.appContext = context;
        this.appActivity = activity;
        this.soundManager = SoundManager.get(appContext);

        this.btnAnimation = AnimationUtils.loadAnimation(
                context, R.anim.fly_right);

        this.btnAnimation.setAnimationListener(new OnAnimListener(
                appContext, appActivity));

        this.btn = activity.findViewById(resId);
        this.btn.setOnClickListener(new ClickListener());
    }

    protected void onAnimEnd() {
    }

    public Context getContext() {
        return this.appContext;
    }

    public AppCompatActivity getActivity() {
        return this.appActivity;
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            btn.startAnimation(btnAnimation);
        }
    }

    class OnAnimListener implements Animation.AnimationListener {

        Context appContext;
        AppCompatActivity appActivity;

        public OnAnimListener(Context context, AppCompatActivity activity) {
            this.appContext = context;
            this.appActivity = activity;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            soundManager.play(UIClip.GENERAL_BUTTON_CLICK);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            btn.setVisibility(View.INVISIBLE);
            onAnimEnd();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
