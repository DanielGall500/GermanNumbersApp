package com.example.germanmemoriserapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.NewActivityManager;

public class GameOverScreen extends AppCompatActivity {

    private ImageButton retryBtn, menuBtn;
    private TextView scoreResultView;
    private String gameScore;
    private Animation congratsAnim;
    private TextView congratsTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over_screen);

        congratsAnim = AnimationUtils.loadAnimation(this, R.anim.congrats_spin);
        congratsTxtView = findViewById(R.id.congratTxtView);
        congratsTxtView.startAnimation(congratsAnim);

        retryBtn = findViewById(R.id.retryBtn);
        menuBtn = findViewById(R.id.menuBtn);
        scoreResultView = findViewById(R.id.scoreView);

        String scoreKey = getString(R.string.score_key);
        String score = retrieveScoreFromGame(scoreKey);

        scoreResultView.setText(getScoreString(score));

        menuBtn.setOnClickListener(new MenuButtonListener(this,this));

        retryBtn.setOnClickListener(new RetryButtonListener(this,this));

    }

    private TextView getScoreView() {
        return this.scoreResultView;
    }

    public String getScoreString(String score) {
        return String.format("%s seconds!", score);
    }

    private String retrieveScoreFromGame(String key) {
        Intent moveFromGame = getIntent();
        return moveFromGame.getStringExtra(key);
    }

    public String getScore() {
        return gameScore;
    }

    private void setScore(String score) {
        gameScore = score;
    }

    private class MenuButtonListener implements View.OnClickListener {

        private Context appContext;
        private AppCompatActivity appActivity;

        public MenuButtonListener(Context mContext, AppCompatActivity mActivity) {
            this.appContext = mContext;
            this.appActivity = mActivity;
        }

        @Override
        public void onClick(View v) {
            NewActivityManager listener = new NewActivityManager();
            listener.move(appContext, appActivity, MenuScreen.class);
        }
    }

    private class RetryButtonListener implements View.OnClickListener {

        private Context context;
        private AppCompatActivity activity;

        public RetryButtonListener(Context context, AppCompatActivity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            NewActivityManager listener = new NewActivityManager();


            //TODO: FIX DIFFICULTY LEVEL HERE
            listener.move(context,activity,true,0);
        }
    }


}
