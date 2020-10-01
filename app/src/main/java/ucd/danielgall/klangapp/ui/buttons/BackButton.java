package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;
import ucd.danielgall.klangapp.sound.SoundManager;
import ucd.danielgall.klangapp.sound.elements.UIClip;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;

public class BackButton {

    NextActivityManager activityListener;
    private Context appContext;
    private AppCompatActivity appActivity;
    private Class moveToActivity;
    private ImageButton backButton;
    private Animation backBtnAnimation;
    private SoundManager soundManager;

    public BackButton(Context appContext, AppCompatActivity appActivity, int buttonId, Class moveTo) {
        this.appContext = appContext;
        this.appActivity = appActivity;
        this.moveToActivity = moveTo;

        this.soundManager = SoundManager.get(appContext, appActivity);

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
            soundManager.play(UIClip.BACK_BUTTON_CLICK);
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
