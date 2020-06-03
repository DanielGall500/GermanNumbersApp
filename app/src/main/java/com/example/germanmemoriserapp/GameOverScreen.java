package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverScreen extends AppCompatActivity {

    Intent moveToNewGame, moveToMenu;

    Button GO_backToMenu, GO_retry;

    private String backToMenuTxt = "MENU";
    private String retryTxt = "RETRY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        GO_backToMenu = findViewById(R.id.GO_backToMenu);
        GO_retry = findViewById(R.id.GO_retry);

        GO_backToMenu.setText(backToMenuTxt);
        GO_retry.setText(retryTxt);


        moveToNewGame = new Intent(GameOverScreen.this, MainActivity.class);
        moveToMenu = new Intent(GameOverScreen.this, MenuScreen.class);

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
}
