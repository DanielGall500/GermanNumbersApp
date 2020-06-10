package com.example.germanmemoriserapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;

public class GameOverScreen extends AppCompatActivity {

    private Button retryBtn;
    private Button backToMenuBtn;
    private TextView scoreResultView;
    private String gameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        retryBtn = findViewById(R.id.GO_retry);
        backToMenuBtn = findViewById(R.id.GO_backToMenu);
        scoreResultView = findViewById(R.id.GO_scoreResultsView);

        String scoreKey = getString(R.string.score_key);
        String score = retrieveScoreFromGame(scoreKey);

        setScoreString(getScoreString(score));

        backToMenuBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, MenuScreen.class
        ));

        retryBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, MainActivity.class
        ));
    }

    private TextView getScoreView() {
        return this.scoreResultView;
    }

    private void setScoreString(String result) {
        getScoreView().setText(result);
    }

    public String getScoreString(String score) {
        return String.format("Your Score Is %s seconds!", score);
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
