package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoadScreen extends AppCompatActivity {

    SoundManager soundPlayer;
    Intent moveToGame;

    private int min = 1;
    private int max = 10;
    private int size = 3;

    class AudioHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            loadComplete();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        /* Get our singleton implementation of
         * an audio manager. */
        soundPlayer = SoundManager.get();

        soundPlayer.init(min, max, size, this);

        soundPlayer.setLoadCompleteHandler(new AudioHandler());

        moveToGame = new Intent(this, MainActivity.class);
    }

    private void loadComplete() {
        startActivity(moveToGame);
        finish();
    }
}
