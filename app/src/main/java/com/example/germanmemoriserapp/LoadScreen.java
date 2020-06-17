package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;

public class LoadScreen extends AppCompatActivity {

    private int soundIds[] = new int[] {
            R.raw.nummer_zwei_og, R.raw.nummer_zwei,
            R.raw.nummer_drei, R.raw.nummer_vier,
            R.raw.nummer_funf, R.raw.nummer_sechs,
            R.raw.nummer_sieben, R.raw.nummer_acht,
            R.raw.nummer_neun, R.raw.nummer_zehn
    };

    SoundManager soundPlayer;
    Intent moveToGame;

    Handler tmp;

    class AudioHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("LOADED");
            System.out.println("Amount loaded: " + soundPlayer.getNumLoadedClips());

            tmp = new Handler();

            tmp.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPlayer.play(1);
                }
            }, 5000);

            //loadComplete();
            //soundPlayer.play(1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        /* Get our singleton implementation of
         * an audio manager. */
        soundPlayer = SoundManager.get();

        soundPlayer.setLoadCompleteHandler(new AudioHandler());

        soundPlayer.loadGameSounds(this);

                /*
        soundPlayer = new NumberAudioPlayer(this, 1, AudioManager.STREAM_MUSIC,
                0, new AudioHandler());*/

        moveToGame = new Intent(this, MainActivity.class);
        //moveToGame.putExtra("NumberAudioPlayer", soundPlayer);
    }

    private void loadComplete() {
        startActivity(moveToGame);
        finish();
    }
}
