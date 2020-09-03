package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.audio.SoundManager;
import com.example.germanmemoriserapp.mechanics.Difficulty;

public class LoadScreen extends AppCompatActivity {

    private SoundManager soundPlayer;
    private Intent moveToNextScreen;

    private ProgressBar audioProgressBar;
    private AnimationDrawable loadBtnAnim;
    private ImageView loadBtn;

    private int NUM_CLIPS = 4;
    private int audioProgress = 0;

    class AudioHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            loadComplete();
        }
    }

    class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            audioProgress++;

            //Run on UI thread
            post(new Runnable() {
                @Override
                public void run() {
                    audioProgressBar.setProgress(audioProgress);
                }
            });
        }
    }

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

    private boolean isGame;
    private int loadInformation;


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
        loadInformation = inputToLoadScreen.getIntExtra(infoKey, 0);

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
        loadBtnAnim = (AnimationDrawable)loadBtn.getDrawable();
        loadBtnAnim.start();

        audioProgressBar = findViewById(R.id.audioProgressBar);
        audioProgressBar.setMax(NUM_CLIPS);
        audioProgressBar.setProgress(audioProgress);

        /* Get our singleton implementation of
         * an audio manager. */
        soundPlayer = SoundManager.get();

        /*
        Handle Input to Load Screen
         */
        retrieveLoadScreenInput();

        //Possibly cause problems
        soundPlayer.setLoadCompleteHandler(new AudioHandler());
        soundPlayer.setProgressHandler(new ProgressHandler());


        if(isGame) {
            /*
            Load Audio Files
             */
            soundPlayer.init(true, NUM_CLIPS, this);


            /*
            Move To Game
             */
            moveToNextScreen = new Intent(this, MainActivity.class);
        } else {


            /*Load Audio Files*/
            soundPlayer.init(false, loadInformation, this);

            /*
            Move To Learn Page
             */
            moveToNextScreen = new Intent(this, LearnSelectionScreen.class);
        }
    }

    private void loadComplete() {
        startActivity(moveToNextScreen);
        finish();
    }
}
