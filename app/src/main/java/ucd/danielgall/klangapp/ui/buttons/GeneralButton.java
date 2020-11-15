package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.sound.SoundSystem;
import ucd.danielgall.klangapp.sound.elements.UIClip;

public abstract class GeneralButton {
    private Animation btnAnimation;

    private SoundSystem soundSystem;

    private AppCompatActivity appActivity;
    private Context appContext;

    private ImageButton btn;

    public GeneralButton(Context context, AppCompatActivity activity, int resId) {
        this.appActivity = activity;
        this.appContext = context;

        soundSystem = new SoundSystem(appContext);

        btn = appActivity.findViewById(resId);

        loadComponents();
    }

    //Implement What To Do Once Animation Finishes
    protected abstract void onAnimEnd();

    public Context getContext() {
        return this.appContext;
    }

    public AppCompatActivity getActivity() {
        return this.appActivity;
    }

    public ImageButton getButton() {
        return this.btn;
    }

    private void loadComponents() {
        this.btnAnimation = AnimationUtils.loadAnimation(
                getContext(), R.anim.fly_right);

        this.btnAnimation.setAnimationListener(new OnAnimListener(
                getContext(), getActivity()));

        btn.setOnClickListener(new ClickListener());
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
            soundSystem.play(UIClip.GENERAL_BUTTON_CLICK);

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
