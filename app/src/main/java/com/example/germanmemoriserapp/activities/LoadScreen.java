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
    private Intent moveToGame;

    private ProgressBar audioProgressBar;
    private AnimationDrawable loadBtnAnim;
    private ImageView loadBtn;

    private int NUM_CLIPS = 4;

    private Difficulty gameDifficulty = new Difficulty(Difficulty.Level.BEGINNER);

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
            System.out.println("UPDATE PROGRESS");
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

        soundPlayer.init(gameDifficulty, NUM_CLIPS, this);

        soundPlayer.setLoadCompleteHandler(new AudioHandler());
        soundPlayer.setProgressHandler(new ProgressHandler());

        moveToGame = new Intent(this, MainActivity.class);
    }

    private void loadComplete() {
        startActivity(moveToGame);
        finish();
    }
}
