package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverScreen extends AppCompatActivity {

    Intent moveToNewGame, moveToMenu, moveFromGame;

    Button GO_backToMenu, GO_retry;
    TextView scoreResultView;

    private String backToMenuTxt = "MENU";
    private String retryTxt = "RETRY";

    String gameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        moveToNewGame = new Intent(GameOverScreen.this, MainActivity.class);
        moveToMenu = new Intent(GameOverScreen.this, MenuScreen.class);

        moveFromGame = getIntent();
        gameScore = moveFromGame.getStringExtra(getString(R.string.score_key));

        GO_backToMenu = findViewById(R.id.GO_backToMenu);
        GO_retry = findViewById(R.id.GO_retry);
        scoreResultView = findViewById(R.id.GO_scoreResultsView);

        GO_backToMenu.setText(backToMenuTxt);
        GO_retry.setText(retryTxt);
        scoreResultView.setText(getScoreResult());

        GO_backToMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(moveToMenu);
            }
        });

        GO_retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(moveToNewGame);
            }
        });

    }

    private String getScoreResult()
    {
        return String.format("Your Score Is %s seconds!", gameScore);
    }
}
