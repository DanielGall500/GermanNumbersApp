package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        enterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Parse input
                String strInput = enterNumberBox.getText().toString();
                int INPUT = Integer.parseInt(strInput);

                GAME.parse(INPUT);

                //Change Text Views
                if(!GAME.isEndOfGame())
                {
                    GAME.newTurn();

                    enterNumberBox.setText(emptyTxt);
                    tmpNumberView.setText(String.valueOf(GAME.getNumber()));
                    scoreView.setText(String.valueOf(GAME.getScore()));
                }
                else
                {
                    scoreView.setText(String.valueOf(GAME.getScore()));
                    tmpNumberView.setText("Game Over !");
                }
            }
        });


    }
}
