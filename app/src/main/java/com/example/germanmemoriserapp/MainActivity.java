package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    int[] digitIds = new int[] {
            R.id.digitZeroBtn, R.id.digitOneBtn, R.id.digitTwoBtn,
            R.id.digitThreeBtn, R.id.digitFourBtn, R.id.digitFiveBtn,
            R.id.digitSixBtn, R.id.digitSevenBtn, R.id.digitEightBtn,
            R.id.digitNineBtn,
    };

    //Settings
    final int MIN_NUM = 1;
    final int MAX_NUM = 10;
    final int NUM_TURNS = 3;
    final int NUM_DIGITS = 10;

    Intent moveToGOScreen;

    String enterNumberTxt = "Got it!";
    final String emptyTxt = "";

    EditText enterNumberBox;
    TextView tmpNumberView;
    Button enterButton;
    TextView timerView;

    Keyboard digitKeyboard;

    ImageButton[] keyboardButtons;

    Game GAME;
    Timer timer;

    HashMap<Integer, Integer> associatedDigits = new HashMap<>();

    InputHandler handler = new InputHandler();

    class InputHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            handleInput();
        }
    }

    private void handleInput()
    {
        String userInput = digitKeyboard.getInput();

        //Don't Accept Invalid Input
        if(!GAME.isValidInput(userInput))
            return;

        int INPUT = parse(userInput);

        boolean correctNumber = GAME.play(INPUT);

        if(!correctNumber)
        {
            return;
        }
        else if(GAME.isEndOfGame())
        {
            timer.stop();

            //Transfer Score Data
            moveToGOScreen.putExtra(getString(R.string.score_key),
                    String.valueOf(timer.getPreviousResult()));

            startActivity(moveToGOScreen);
        }
        else
        {
            GAME.newTurn();
            updateGfx(enterNumberBox);
        }
    }

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
        timerView = findViewById(R.id.timerView);

        keyboardButtons = new ImageButton[NUM_DIGITS];

        for (int i = 0; i < NUM_DIGITS; i++)
            keyboardButtons[i] = findViewById(digitIds[i]);

        digitKeyboard = new Keyboard(keyboardButtons, enterNumberBox, handler);

        //Set Initial UI Parameters
        enterButton.setText(enterNumberTxt);
        updateGfx(enterNumberBox);

        //Begin the timer
        //timeHandler.postDelayed(timerRunnable, 0);
        timer = new Timer(timerView);
        timer.begin();
    }

    private int parse(String userInput)
    {
        return Integer.parseInt(userInput);
    }

    private void updateGfx(EditText entryBox)
    {
        clearAllInput(entryBox);

        //To Be Deleted
        String number = String.valueOf(GAME.getNumber());
        tmpNumberView.setText(number);
    }

    private void clearAllInput(EditText entryBox)
    {
        digitKeyboard.clearInput();
        entryBox.setText(digitKeyboard.getInput());
    }
}
