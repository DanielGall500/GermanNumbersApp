package com.example.germanmemoriserapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.mechanics.Game;
import com.example.germanmemoriserapp.mechanics.Game.GAME_STATE;
import com.example.germanmemoriserapp.ui.Keyboard;
import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.Timer;

public class MainActivity extends AppCompatActivity {

    //10 Digits + 1 Back Button
    final int SIZE_KEYB = 11;

    int[] digitIds = new int[]{
            R.id.digitZeroBtn, R.id.digitOneBtn, R.id.digitTwoBtn,
            R.id.digitThreeBtn, R.id.digitFourBtn, R.id.digitFiveBtn,
            R.id.digitSixBtn, R.id.digitSevenBtn, R.id.digitEightBtn,
            R.id.digitNineBtn, R.id.backBtn
    };

    InputHandler handler;
    Intent moveToGOScreen;
    EditText enterNumberBox;
    TextView tmpNumberView;
    TextView timerView;
    Keyboard digitKeyboard;
    Timer timer;
    Game GAME;

    /*
    Called when the user presses a button on
    the game's keyboard.
     */
    class InputHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String userInput = digitKeyboard.getInput();
            handleInput(userInput);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: safety check to ensure sounds load

        handler = new InputHandler();

        moveToGOScreen = new Intent(MainActivity.this,
                GameOverScreen.class);

        GAME = new Game();

        //Find UI Elements
        enterNumberBox = findViewById(R.id.enterNumberBox);
        tmpNumberView = findViewById(R.id.tmpNumberView);
        timerView = findViewById(R.id.timerView);

        ImageButton[] keyboardButtons = new ImageButton[SIZE_KEYB];

        for (int i = 0; i < SIZE_KEYB; i++)
            keyboardButtons[i] = findViewById(digitIds[i]);

        digitKeyboard = new Keyboard(keyboardButtons, SIZE_KEYB, enterNumberBox, handler);

        //Begin the timer
        //timeHandler.postDelayed(timerRunnable, 0);
        timer = new Timer(timerView);
        timer.begin();

        GAME.begin();

        //Set Initial UI Parameters
        updateGfx(enterNumberBox);
    }

    private void handleInput(String userInput) {

        System.out.println("input: " + userInput);

        GAME_STATE nextState = GAME.getState(userInput);

        System.out.println("Next State: " + nextState.toString());

        GAME.execute(nextState);

        if(nextState.equals(GAME_STATE.NEW_TURN)) {
            onNewTurn();
        }
        else if(nextState.equals(GAME_STATE.GAME_OVER)) {
            onGameOver();
        }
    }

    private void onNewTurn() {
        updateGfx(enterNumberBox);
    }

    private void onGameOver() {
        timer.stop();

        //Transfer Score Data
        moveToGOScreen.putExtra(getString(R.string.score_key),
                String.valueOf(timer.getPreviousResult()));

        startActivity(moveToGOScreen);
        this.finish();
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
}
