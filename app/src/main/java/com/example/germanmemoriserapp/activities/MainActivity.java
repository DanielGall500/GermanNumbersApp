package com.example.germanmemoriserapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
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

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        GAME = new Game();

        //TODO: safety check to ensure sounds load

        handler = new InputHandler();

        moveToGOScreen = new Intent(MainActivity.this,
                GameOverScreen.class);

        //Find UI Elements
        enterNumberBox = findViewById(R.id.enterNumberBox);
        tmpNumberView = findViewById(R.id.tmpNumberView);
        timerView = findViewById(R.id.timerView);

        digitKeyboard = setupKeyboard(digitIds, SIZE_KEYB);
        timer = new Timer(timerView);

        timer.begin();
        GAME.begin();

        //Set Initial UI Parameters
        updateGfx(enterNumberBox);
    }

    private void handleInput(String userInput) {

        GAME_STATE nextState = GAME.getState(userInput);

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

    private Keyboard setupKeyboard(int[] digitIds, int N) {
        ImageButton[] keyboardButtons = new ImageButton[N];

        for (int i = 0; i < N; i++)
            keyboardButtons[i] = findViewById(digitIds[i]);

        return new Keyboard(keyboardButtons, N, enterNumberBox, handler);
    }
}
