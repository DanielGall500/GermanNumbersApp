package com.example.germanmemoriserapp.activities.game;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.MenuActivityManager;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Difficulty;
import com.example.germanmemoriserapp.mechanics.game.Game;
import com.example.germanmemoriserapp.mechanics.game.Game.GAME_STATE;
import com.example.germanmemoriserapp.mechanics.score.ScoreBoardManager;
import com.example.germanmemoriserapp.mechanics.score.Timer;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.elements.NumberClip;
import com.example.germanmemoriserapp.ui.Keyboard;
import com.example.germanmemoriserapp.ui.Keyboard.BUTTON_STATE;
import com.example.germanmemoriserapp.ui.buttons.ButtonResource;
import com.example.germanmemoriserapp.utilities.AppCleanup;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends CleanupActivity {

    //10 Digits + 1 Back Button
    final int SIZE_KEYB = 11;
    final int NUM_DIGITS = 10;

    final int WAIT_BEFORE_PLAYING = 1000;

    int[] digitIds = new int[]{
            R.id.digitZeroBtn, R.id.digitOneBtn, R.id.digitTwoBtn,
            R.id.digitThreeBtn, R.id.digitFourBtn, R.id.digitFiveBtn,
            R.id.digitSixBtn, R.id.digitSevenBtn, R.id.digitEightBtn,
            R.id.digitNineBtn, R.id.relistenBtn
    };

    EditText enterNumberBox;
    TextView timerView;
    Keyboard digitKeyboard;
    Timer timer;
    Game GAME;
    ImageButton relistenBtn;
    Animation relistenAnim;

    TextView relistenUpdatetxt;
    TextView lifeUpdateTxt;
    /*
    Records our score at the end of the game.
     */
    ScoreBoardManager scoreBoard = new ScoreBoardManager(this);
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
    private Difficulty.Level gameDifficulty;

    @Override
    public void onBackPressed() {
        AppCleanup cleaner = new AppCleanup(this,this);
        MenuActivityManager menuManager = new MenuActivityManager(this,this);

        cleaner.run();
        menuManager.run();
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

        //Match particular button Ids to their numerical value
        digitIdReference = setupDigitRef(digitIds);
        enterNumberBox = findViewById(R.id.enterNumberBox);
        timerView = findViewById(R.id.timeUpdateTxt);

        /* Setup Keyboard */
        digitKeyboard = new Keyboard(inputFieldH, buttonUiH, gameStateH,
                digitIdReference, GAME);

        setAllButtonListeners(digitKeyboard);

        /* Setup New Game */
        ArrayList<NumberClip> loadedNumbersArr = SoundManager.get(
                this).getLoadedNumbers();

        gameDifficulty = getDifficultyIdFromLoad();
        GAME = new Game(this, gameDifficulty, loadedNumbersArr);

        /* Lives/Listens Buttons */
        relistenBtn = findViewById(R.id.relistenBtn);
        relistenAnim = AnimationUtils.loadAnimation(this, R.anim.relisten_anim);
        relistenBtn.setOnClickListener(new RelistenBtnHandler());

        /* Lives/Listens Text */
        relistenUpdatetxt = findViewById(R.id.relistenUpdateTxt);
        lifeUpdateTxt = findViewById(R.id.lifeUpdateTxt);
        setupLives();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Begin Game */
                GAME.begin();
                onNewTurn();

                /* Start Timer */
                timer = new Timer(timerView);
                timer.begin();

                //Allow the user to input to keyboard
                digitKeyboard.setGameReady(true);
            }
        }, WAIT_BEFORE_PLAYING);
    }

    private int getDifficultyId() {
        Intent fromLoadScreen = getIntent();

        String key = getString(R.string.load_screen_information);

        int difficultyId = fromLoadScreen.getIntExtra(key, -1);

        return difficultyId;
    }

    private void updateFieldText(String s) {
        sendMsgToHandler(inputFieldH, s);
    }

    private void sendMsgToHandler(Handler h, String s) {
        Message msg = new Message();
        msg.obj = s;
        msg.setTarget(h);
        msg.sendToTarget();
    }

    private void setupLives() {
        int nLives = GAME.getLives();
        int nRelistens = GAME.getRelistens();

        lifeUpdateTxt.setText(String.valueOf(nLives));
        relistenUpdatetxt.setText(String.valueOf(nRelistens));
    }

    /*
    Called if we want to update the state of
    a keyboard's button.
     */

    private Difficulty.Level getDifficultyIdFromLoad() {
        String key = getString(
                R.string.load_screen_information);

        Intent fromLoad = getIntent();

        int diffId = fromLoad.getIntExtra(key, -1);

        return Difficulty.getLevel(diffId);
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

    private void onGameOver() {
        timer.stop();

        int timerValue = timer.getPreviousResult();

        String scoreDataKey = getString(R.string.score_key);
        String difficultyKey = getString(R.string.game_load_difficulty_key);

        NextActivityManager nextActivityManager = new NextActivityManager(this, this);
        nextActivityManager.setNextActivity(GameOverScreen.class);

        if (!GAME.gameLost()) {
            /* Transfer Score Data To Game Over Screen */
            nextActivityManager.addInformation(scoreDataKey, timerValue);

            /* Update Scores File */
            scoreBoard.update(getDifficultyId(), timerValue);
        } else {
            nextActivityManager.addInformation(scoreDataKey, Game.GAME_LOST_VALUE);
        }

        nextActivityManager.addInformation(difficultyKey, getDifficultyId());

        nextActivityManager.run();
    }

    private void clearAllInput(EditText entryBox) {
        entryBox.setText("");
    }

    private ImageButton getBtn(int digit) {
        return findViewById(digitIds[digit]);
    }

    private void setAllButtonListeners(View.OnClickListener listener) {
        for (int i = 0; i < SIZE_KEYB; i++) {
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

    class RelistenBtnHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (GAME.canRelisten()) {
                //Re-listen button animation
                findViewById(v.getId()).startAnimation(relistenAnim);

                //Play the clip & Update GFX
                GAME.relisten(relistenUpdatetxt);
            }
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

            /* Update our text entry field. */
            updateFieldText(digitKeyboard.getInput());

            //Retrieve next state of the game
            GAME_STATE nextState = GAME.getState(userInput, lifeUpdateTxt);

            //Execute this action in the backend
            GAME.execute(nextState);

            //Execute this action in the frontend
            if (nextState.equals(GAME_STATE.NEW_TURN)) {
                onNewTurn();

                int nextNum = GAME.getNumber();
                digitKeyboard.updateCorrectNumber(nextNum);
            } else if (nextState.equals(GAME_STATE.GAME_WON)) {
                onGameOver();
            } else if (nextState.equals(GAME_STATE.GAME_LOST)) {
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
}
