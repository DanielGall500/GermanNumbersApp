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
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;
import com.example.germanmemoriserapp.mechanics.Game;
import com.example.germanmemoriserapp.sound.SoundManager;

public class GameOverScreen extends AppCompatActivity {

    private ImageButton retryBtn, menuBtn;
    private TextView scoreResultView;
    private String gameScore;
    private Animation congratsAnim;
    private TextView congratsTxtView;

    private boolean gameLost;
    private SoundManager soundManager;

    private int difficulty;

    /*
    TODO:
    fix difficulty

    fix buttons
     */

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

        /* Unload Game Sounds */
        soundManager = SoundManager.get(this);

        Thread releaseClips = new Thread(new Runnable() {
            @Override
            public void run() {
                soundManager.releaseAllGameUIClips();
                soundManager.releaseAllNumberClips();
            }
        });

        releaseClips.run();

        updateResultsView();

        this.difficulty = getDifficulty();

        /* Listeners */
        menuBtn.setOnClickListener(new MenuButtonListener(this,this));
        retryBtn.setOnClickListener(new RetryButtonListener(this,this));
    }

    private void updateResultsView() {
        String scoreKey = getString(R.string.score_key);
        String score = retrieveScoreFromGame(scoreKey);

        if(!gameLost) {
            scoreResultView.setText(getScoreString(score));
        }
        else {
            scoreResultView.setText(Game.GAME_LOST_TEXT);
        }
    }

    private int getDifficulty() {
        String key = getString(R.string.game_load_difficulty_key);

        int diff = getIntent().getIntExtra(key,-1);

        return diff;
    }

    private TextView getScoreView() {
        return this.scoreResultView;
    }

    public String getScoreString(String score) {
        return String.format("%s seconds!", score);
    }

    private String retrieveScoreFromGame(String key) {
        Intent moveFromGame = getIntent();
        int result = moveFromGame.getIntExtra(key, Game.GAME_LOST_VALUE);

        if(result == Game.GAME_LOST_VALUE) {
            gameLost = true;
        }

        return String.valueOf(result);
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
            NextActivityManager nextActivityManager = new NextActivityManager(
                    appContext,appActivity);
            nextActivityManager.setNextActivity(MenuScreen.class);
            nextActivityManager.run();
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
            NextActivityManager nextActivityManager = new NextActivityManager(context,activity);
            nextActivityManager.setNextActivity(LoadAudioScreen.class);

            String isGameKey = getString(R.string.load_screen_isGameBoolean);
            String loadInfoKey = getString(R.string.load_screen_information);

            nextActivityManager.addInformation(isGameKey, true);
            nextActivityManager.addInformation(loadInfoKey,difficulty);

            nextActivityManager.run();
        }
    }


}
