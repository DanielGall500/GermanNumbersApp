package com.example.germanmemoriserapp.activities.load;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.game.GameScreen;
import com.example.germanmemoriserapp.activities.learn.LearnSelectionScreen;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Game;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.elements.SoundElement;
import com.example.germanmemoriserapp.sound.elements.UIClip;
import com.example.germanmemoriserapp.utilities.LearnPage;
import com.example.germanmemoriserapp.utilities.NumberGenerator;

import java.util.ArrayList;

public class LoadAudioScreen extends AppCompatActivity {

    NextActivityManager moveToNextScreen;
    NumberGenerator generator = new NumberGenerator();
    ArrayList<SoundElement> generatedArr;
    private SoundManager soundPlayer;
    private ProgressBar audioProgressBar;
    private AnimationDrawable loadBtnAnim;
    private ImageView loadBtn;
    private int NUMBER_CLIPS = Game.NUMBER_CLIPS;
    private int UI_CLIPS = Game.UI_CLIPS;
    private int TOTAL_CLIPS = NUMBER_CLIPS + UI_CLIPS;
    private int LEARN_PAGE_CLIPS = 10;

    /*
    Load Screen

    Can Load:
    - The Game
        - Input: isGame, Difficulty
        - Output: Loaded Audio

    - Learning Activities
        - Input: isGame, Page Number
        - Output: Loaded Audio, German String Arr, Digit String Arr
     */
    private int audioProgress = 0;
    private boolean isGame;
    private int loadInformation;

    public Class getNextScreen(boolean isGame) {
        if (isGame) {
            return GameScreen.class;
        } else {
            return LearnSelectionScreen.class;
        }
    }

    private void retrieveLoadScreenInput() {
        Intent inputToLoadScreen = getIntent();

        /*
        This key tells us if we're loading a game or a learning page
         */
        final String isGameKey = getString(R.string.load_screen_isGameBoolean);
        isGame = inputToLoadScreen.getBooleanExtra(
                isGameKey, true);

        /*
        Depending on if we are loading a game or a learning page,
        this key will tell us either the difficulty level or the page
        to load.
         */
        final String infoKey = getString(R.string.load_screen_information);
        loadInformation = inputToLoadScreen.getIntExtra(infoKey, 1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_load_screen);


        loadBtn = findViewById(R.id.loadBtn);
        loadBtnAnim = (AnimationDrawable) loadBtn.getDrawable();
        loadBtnAnim.start();

        audioProgressBar = findViewById(R.id.audioProgressBar);
        audioProgressBar.setMax(TOTAL_CLIPS);
        audioProgressBar.setProgress(audioProgress);

        /* Get our singleton implementation of
         * an audio manager. */
        soundPlayer = SoundManager.get(this);

        /* Handle Input to Load Screen */
        retrieveLoadScreenInput();

        if (isGame) {
            soundPlayer.setOnAudioLoadedHandler(new GameAudioHandler());

            /* Sounds For Number Pronunciation */
            generator = new NumberGenerator();
            generatedArr = generator.generateRandom(
                    Game.MIN_NUM, Game.MAX_NUM, NUMBER_CLIPS);

            /* UI Sound Effects */
            generatedArr.add(UIClip.GAME_CORRECT);
            generatedArr.add(UIClip.GAME_INCORRECT);
            generatedArr.add(UIClip.GAME_WON);
            generatedArr.add(UIClip.GAME_LOST);

            soundPlayer.loadAll(generatedArr);
        } else {
            soundPlayer.setOnAudioLoadedHandler(new LearnPageAudioHandler());

            int[] minMax = LearnPage.getMinMax(loadInformation);
            generatedArr = generator.generateNormal(minMax[0], minMax[1]);
            soundPlayer.loadAll(generatedArr);
        }


    }

    private void loadComplete(Class nextScreen) {
        /*
        Store relevant information for learning page.
        */
        String key = getString(R.string.load_screen_information);

        /* Move To Learn Page */
        System.out.println("Passing : " + loadInformation +
                " To " + key);

        moveToNextScreen = new NextActivityManager(this, this);
        moveToNextScreen.setNextActivity(nextScreen);
        moveToNextScreen.addInformation(key, loadInformation);
        moveToNextScreen.run();
    }

    class GameAudioHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            audioProgress++;

            if (audioProgress == TOTAL_CLIPS) {
                loadComplete(getNextScreen(isGame));
            } else {
                //Run on UI thread
                post(new Runnable() {
                    @Override
                    public void run() {
                        audioProgressBar.setProgress(audioProgress);
                    }
                });
            }
        }
    }

    class LearnPageAudioHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            audioProgress++;

            if (audioProgress == LEARN_PAGE_CLIPS) {
                loadComplete(getNextScreen(isGame));
            } else {
                //Run on UI thread
                post(new Runnable() {
                    @Override
                    public void run() {
                        audioProgressBar.setProgress(audioProgress);
                    }
                });
            }
        }
    }
}
