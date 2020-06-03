package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent moveToGOScreen;

    private String enterNumberTxt = "Got it!";
    private final String emptyTxt = "";

    private final int TOTAL_TURNS = 3;

    EditText enterNumberBox;
    Button enterButton;
    TextView tmpNumberView;
    EditText scoreView;

    Game GAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GAME = new Game(TOTAL_TURNS);

        enterNumberBox = findViewById(R.id.enterNumberBox);
        tmpNumberView = findViewById(R.id.tmpNumberView);
        enterButton = findViewById(R.id.enterButton);
        scoreView = findViewById(R.id.scoreView);

        tmpNumberView.setText(String.valueOf(GAME.getNumber()));
        scoreView.setText(String.valueOf(GAME.getScore()));
        enterButton.setText(enterNumberTxt);

        moveToGOScreen = new Intent(MainActivity.this, GameOverScreen.class);

        enterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                int INPUT = parseText(enterNumberBox);

                GAME.play(INPUT);

                System.out.println("New Score: " + GAME.getScore());

                if(GAME.isEndOfGame())
                {
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

    private int parseText(EditText entryBox)
    {
        String strInput = entryBox.getText().toString();

        return Integer.parseInt(strInput);
    }

    private void updateGfx(EditText entryBox, EditText scoreBox)
    {
        clearEntryBox_GFX(entryBox);
        updateScoreView_GFX(scoreBox);

        //To Be Deleted
        String number = String.valueOf(GAME.getNumber());
        System.out.println("Updates: " + number);
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
