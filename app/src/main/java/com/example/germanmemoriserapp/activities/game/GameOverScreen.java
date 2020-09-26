package com.example.germanmemoriserapp.activities.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.MenuActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Game;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.ui.buttons.MenuButton;
import com.example.germanmemoriserapp.ui.buttons.RetryButton;

public class GameOverScreen extends AppCompatActivity {

    private TextView scoreResultView;

    private boolean gameLost;
    private SoundManager soundManager;

    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over_screen);

        scoreResultView = findViewById(R.id.scoreView);

        /* Get Information From Previous Screen */
        updateResultsView();
        updateDifficulty();

        /* Buttons */
        new RetryButton(this, this, R.id.retryBtn, difficulty);
        new MenuButton(this, this, R.id.menuBtn,
                new MenuActivityManager(this, this));
    }

    private void updateResultsView() {
        String scoreKey = getString(R.string.score_key);
        String score = retrieveScoreFromGame(scoreKey);

        if (!gameLost) {
            scoreResultView.setText(getScoreString(score));
        } else {
            scoreResultView.setText(Game.GAME_LOST_TEXT);
        }
    }

    private void updateDifficulty() {
        String key = getString(R.string.game_load_difficulty_key);

        this.difficulty = getIntent().getIntExtra(key, -1);
    }

    public String getScoreString(String score) {
        return String.format("%s seconds!", score);
    }

    private String retrieveScoreFromGame(String key) {
        Intent moveFromGame = getIntent();
        int result = moveFromGame.getIntExtra(key, Game.GAME_LOST_VALUE);

        if (result == Game.GAME_LOST_VALUE) {
            gameLost = true;
        }

        return String.valueOf(result);
    }
}
