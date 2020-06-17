package com.example.germanmemoriserapp;

import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class WavFilePlayer {

    public WavFilePlayer() {
        Thread streamThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SoundPool player;
                final int wav;
                String path = "/Users/dannyg/AndroidStudioProjects/GermanMemoriserApp/app/src/main/res/raw/nummer_acht.wav";

                player = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

                wav = player.load(path, 1);


                player.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(wav, 100, 100, 0, -1, 1f);
                    }
                });
            }
        });

        streamThread.start();
    }

}
