package com.example.germanmemoriserapp.activities;

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

import com.example.germanmemoriserapp.ui.Keyboard.BUTTON_STATE;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //10 Digits + 1 Back Button
    final int SIZE_KEYB = 11;
    final int NUM_DIGITS = 10;

    int[] digitIds = new int[]{
            R.id.digitZeroBtn, R.id.digitOneBtn, R.id.digitTwoBtn,
            R.id.digitThreeBtn, R.id.digitFourBtn, R.id.digitFiveBtn,
            R.id.digitSixBtn, R.id.digitSevenBtn, R.id.digitEightBtn,
            R.id.digitNineBtn, R.id.backBtn
    };

    int[] unpressedIds = new int[] {
            R.drawable.keyb_0, R.drawable.keyb_1, R.drawable.keyb_2,
            R.drawable.keyb_3, R.drawable.keyb_4, R.drawable.keyb_5,
            R.drawable.keyb_6, R.drawable.keyb_7, R.drawable.keyb_8,
            R.drawable.keyb_9
    };

    InputHandler handler;
    Intent moveToGOScreen;
    EditText enterNumberBox;
    TextView tmpNumberView;
    TextView timerView;
    Keyboard digitKeyboard;
    Timer timer;

    Game GAME;
    //Input INPUT;

    HashMap<Integer, Integer> associatedDigits;

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

    class InputFieldUIListener extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String newStr = (String) msg.obj;
            enterNumberBox.setText(newStr);
        }
    }

    class ButtonUIListener extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle btnData = msg.getData();

            BUTTON_STATE newState = (BUTTON_STATE) btnData.get("STATE");
            int digit = (int) btnData.get("DIGIT");

            ImageButton btn = getBtn(digit);

            System.out.println(String.format("Setting %d to %s", digit, newState.toString()));

            switch(newState) {
                case VALID:
                    btn.setImageResource(R.drawable.keyb_1);
                    break;
                case INVALID:
                    btn.setImageResource(R.drawable.keyb_0);
                    break;
                case UNPRESSED:
                    int unpressedImg = unpressedIds[digit];
                    btn.setImageResource(unpressedImg);
                    break;
            }
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

        associatedDigits = new HashMap<>();

        for (int i = 0; i < NUM_DIGITS; i++) {
            associatedDigits.put(digitIds[i], i);
        }

        //Mediator between the game and the keyboard
        //INPUT = new Input(GAME, new InputHandler());

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

        for(int i = 0; i < SIZE_KEYB; i++) {
            int id = digitIds[i];
            findViewById(id).setOnClickListener(digitKeyboard);
        }

        timer = new Timer(timerView);

        timer.begin();
        GAME.begin();

        //First move
        onNewTurn();
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
        digitKeyboard.updateCorrectNumber(GAME.getNumber());
        digitKeyboard.clear();
        clearAllInput(enterNumberBox);
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
        entryBox.setText(digitKeyboard.getInput());
    }

    private Keyboard setupKeyboard(int[] digitIds, int N) {

        ImageButton[] keyboardButtons = new ImageButton[N];

        for (int i = 0; i < N; i++)
            keyboardButtons[i] = findViewById(digitIds[i]);

        for(ImageButton button : keyboardButtons) {
            button.setOnClickListener(digitKeyboard);
        }

        return new Keyboard(new InputFieldUIListener(), new ButtonUIListener(),
                associatedDigits, handler, GAME);
    }

    private ImageButton getBtn(int digit) {
        //TODO
        return findViewById(digitIds[digit]);
    }
}
