package com.example.germanmemoriserapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;

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

        menuBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, MenuScreen.class
        ));

        retryBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, LoadScreen.class
        ));

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


}
