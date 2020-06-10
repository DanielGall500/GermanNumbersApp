package com.example.germanmemoriserapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Settings
    final int MIN_NUM = 1;
    final int MAX_NUM = 10;
    final int NUM_TURNS = 3;

    //10 Digits + 1 Back Button
    final int SIZE_KEYB = 11;

    int[] digitIds = new int[]{
            R.id.digitZeroBtn, R.id.digitOneBtn, R.id.digitTwoBtn,
            R.id.digitThreeBtn, R.id.digitFourBtn, R.id.digitFiveBtn,
            R.id.digitSixBtn, R.id.digitSevenBtn, R.id.digitEightBtn,
            R.id.digitNineBtn, R.id.backBtn
    };

    Intent moveToGOScreen;

    EditText enterNumberBox;
    TextView tmpNumberView;
    TextView timerView;

    Keyboard digitKeyboard;
    Timer timer;
    Game GAME;

    InputHandler handler = new InputHandler();

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
        timerView = findViewById(R.id.timerView);

        ImageButton[] keyboardButtons = new ImageButton[SIZE_KEYB];

        for (int i = 0; i < SIZE_KEYB; i++)
            keyboardButtons[i] = findViewById(digitIds[i]);

        digitKeyboard = new Keyboard(keyboardButtons, SIZE_KEYB, enterNumberBox, handler);

        //Set Initial UI Parameters
        updateGfx(enterNumberBox);

        //Begin the timer
        //timeHandler.postDelayed(timerRunnable, 0);
        timer = new Timer(timerView);
        timer.begin();
    }

    private int parse(String userInput) {
        return Integer.parseInt(userInput);
    }

    private void updateGfx(EditText entryBox) {
        clearAllInput(entryBox);

        //To Be Deleted
        String number = String.valueOf(GAME.getNumber());
        tmpNumberView.setText(number);
    }

    private void clearAllInput(EditText entryBox) {
        digitKeyboard.clearInput();
        entryBox.setText(digitKeyboard.getInput());
    }

    class InputHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            handleInput();
        }
    }

    private void handleInput() {
        String userInput = digitKeyboard.getInput();

        //Don't Accept Invalid Input
        if (!GAME.isValidInput(userInput))
            return;

        int INPUT = parse(userInput);

        boolean correctNumber = GAME.play(INPUT);

        if (!correctNumber) {
            return;
        } else if (GAME.isEndOfGame()) {
            timer.stop();

            //Transfer Score Data
            moveToGOScreen.putExtra(getString(R.string.score_key),
                    String.valueOf(timer.getPreviousResult()));

            startActivity(moveToGOScreen);
        } else {
            GAME.newTurn();
            updateGfx(enterNumberBox);
        }
    }
}
