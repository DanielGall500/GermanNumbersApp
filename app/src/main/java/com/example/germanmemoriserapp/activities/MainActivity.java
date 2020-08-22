package com.example.germanmemoriserapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.mechanics.Game;
import com.example.germanmemoriserapp.mechanics.Game.GAME_STATE;
import com.example.germanmemoriserapp.ui.ButtonResource;
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
            R.id.digitNineBtn, R.id.relistenAnim
    };

    Intent moveToGOScreen;
    EditText enterNumberBox;
    TextView timerView;
    Keyboard digitKeyboard;
    Timer timer;
    Game GAME;
    ImageButton relistenBtn;
    Animation relistenAnim;

    /*
    Grabs Buttons Images: Unpressed, Correct, Incorrect
     */
    ButtonResource btnRes = new ButtonResource();

    /*
    Matches digit ID to digit value
     */
    HashMap<Integer, Integer> digitIdReference;

    /*
    Initialise our handlers. These will update particular
    parts of our game when called.
     */
    GameStateHandler gameStateH = new GameStateHandler();
    InputFieldUIHandler inputFieldH = new InputFieldUIHandler();
    ButtonUIHandler buttonUiH = new ButtonUIHandler();

    class RelistenBtnHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(relistenAnim);
            GAME.playAudioClip();
        }
    }

    /*
    Called when the user presses a button on
    the game's keyboard.
     */
    class GameStateHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String userInput = (String) msg.obj;

            System.out.println("NEW GAME STATE");

            /*
            Update our text entry field.
             */
            updateFieldText(digitKeyboard.getInput());

            //Retrieve next state of the game
            GAME_STATE nextState = GAME.getState(userInput);

            //Execute this action in the backend
            GAME.execute(nextState);

            //Execute this action in the frontend
            if(nextState.equals(GAME_STATE.NEW_TURN)) {
                onNewTurn();
            }
            else if(nextState.equals(GAME_STATE.GAME_OVER)) {
                onGameOver();
            }
        }
    }

    /*
    Called if we want to update the text
    in our input field.
     */
    class InputFieldUIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String newStr = (String) msg.obj;
            enterNumberBox.setText(newStr);
        }
    }

    /*
    Called if we want to update the state of
    a keyboard's button.
     */

    private void updateFieldText(String s) {
        sendMsgToHandler(inputFieldH, s);
    }

    private void sendMsgToHandler(Handler h, String s) {
        Message msg = new Message();
        msg.obj = s;
        msg.setTarget(h);
        msg.sendToTarget();
    }

    private void sendMsgToHandler(Handler h, Message msg) {
        msg.setTarget(h);
        msg.sendToTarget();
    }

    class ButtonUIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle btnData = msg.getData();

            BUTTON_STATE newState = (BUTTON_STATE) btnData.get("STATE");
            int digit = (int) btnData.get("DIGIT");

            ImageButton btn = getBtn(digit);
            int img = btnRes.getGameBtn(digit, newState);
            btn.setImageResource(img);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Preference: Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        moveToGOScreen = new Intent(MainActivity.this,
                GameOverScreen.class);

        //Match particular button Ids to their numerical value
        digitIdReference = setupDigitRef(digitIds);

        //Find UI Elements
        enterNumberBox = findViewById(R.id.enterNumberBox);
        timerView = findViewById(R.id.timerView);

        digitKeyboard = new Keyboard(inputFieldH, buttonUiH, gameStateH,
                digitIdReference, GAME);

        setAllButtonListeners(digitKeyboard);

        relistenBtn = findViewById(R.id.relistenAnim);
        relistenAnim = AnimationUtils.loadAnimation(this, R.anim.relisten_anim);

        relistenBtn.setOnClickListener(new RelistenBtnHandler());

        GAME = new Game();
        GAME.begin();

        onNewTurn();

        timer = new Timer(timerView);
        timer.begin();
    }

    private void onNewTurn() {
        digitKeyboard.updateCorrectNumber(GAME.getNumber());
        digitKeyboard.clear();

        enterNumberBox.setTextColor(Color.GREEN);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterNumberBox.setText("");
                enterNumberBox.setTextColor(Color.BLACK);
            }
        }, 2000);

    }

    private void onNoChange(boolean valid) {

        enterNumberBox.setTextColor(Color.RED);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterNumberBox.setText("");
                enterNumberBox.setTextColor(Color.BLACK);
            }
        }, 1000);


    }

    private void onValidInput() {

    }

    private void onInvalidInput() {

    }

    private void onGameOver() {
        timer.stop();

        //Transfer Score Data
        moveToGOScreen.putExtra(getString(R.string.score_key),
                String.valueOf(timer.getPreviousResult()));

        startActivity(moveToGOScreen);

        this.finish();
    }

    private void clearAllInput(EditText entryBox) {
        entryBox.setText("");
    }

    private ImageButton getBtn(int digit) {
        //TODO
        return findViewById(digitIds[digit]);
    }

    private void setAllButtonListeners(View.OnClickListener listener) {
        for(int i = 0; i < SIZE_KEYB; i++) {
            int id = digitIds[i];
            findViewById(id).setOnClickListener(listener);
        }
    }

    /*
    A reference which matches the integer id of a button
    to its numerical digit value.
     */
    private HashMap<Integer, Integer> setupDigitRef(int[] digitIds) {
        HashMap<Integer, Integer> digitReference = new HashMap<>();

        for (int i = 0; i < NUM_DIGITS; i++)
            digitReference.put(digitIds[i], i);

        return digitReference;
    }
}
