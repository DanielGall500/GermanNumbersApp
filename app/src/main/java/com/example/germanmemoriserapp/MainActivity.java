package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Settings
    final int MIN_NUM = 1;
    final int MAX_NUM = 10;
    final int NUM_TURNS = 3;

    Intent moveToGOScreen;

    String enterNumberTxt = "Got it!";
    final String emptyTxt = "";

    EditText enterNumberBox;
    TextView tmpNumberView;
    EditText scoreView;
    Button enterButton;
    TextView timerView;

    Game GAME;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveToGOScreen = new Intent(MainActivity.this,
                GameOverScreen.class);

        GAME = new Game(MIN_NUM, MAX_NUM, NUM_TURNS);

        //Find UI Elements
        enterNumberBox = findViewById(R.id.enterNumberBox);
        tmpNumberView = findViewById(R.id.tmpNumberView);
        enterButton = findViewById(R.id.enterButton);
        scoreView = findViewById(R.id.scoreView);
        timerView = findViewById(R.id.timerView);

        //Set Initial UI Parameters
        enterButton.setText(enterNumberTxt);
        updateGfx(enterNumberBox, scoreView);

        //Begin the timer
        //timeHandler.postDelayed(timerRunnable, 0);
        timer = new Timer(timerView);
        timer.begin();

        enterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String userInput = enterNumberBox.getText().toString();

                //Don't Accept Invalid Input
                if(!GAME.isValidInput(userInput))
                    return;

                int INPUT = parseText(userInput);

                GAME.play(INPUT);

                if(GAME.isEndOfGame())
                {
                    timer.stop();
                    startActivity(moveToGOScreen);
                }
                else
                {
                    GAME.newTurn();
                    updateGfx(enterNumberBox, scoreView);
                }
            }
        });
    }

    private int parseText(String userInput)
    {
        return Integer.parseInt(userInput);
    }

    private void updateGfx(EditText entryBox, EditText scoreBox)
    {
        clearEntryBox_GFX(entryBox);
        updateScoreView_GFX(scoreBox);

        //To Be Deleted
        String number = String.valueOf(GAME.getNumber());
        tmpNumberView.setText(number);
    }

    private void clearEntryBox_GFX(EditText entryBox)
    {
        entryBox.setText(emptyTxt);
    }

    private void updateScoreView_GFX(EditText scoreBox)
    {
        String score = String.valueOf(GAME.getScore());
        scoreBox.setText(score);
    }
}
