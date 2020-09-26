package com.example.germanmemoriserapp.utilities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.sound.SoundManager;

public class AppCleanup {

    private Context appContext;
    private AppCompatActivity appActivity;

    public AppCleanup(Context appContext, AppCompatActivity appActivity) {
        this.appContext = appContext;
        this.appActivity = appActivity;
    }

    public void run() {
        /* Release All Audio Clips */
        if(SoundManager.isActive()) {
            SoundManager.get(appContext).releaseAllNumberClips();
            SoundManager.get(appContext).releaseAllGameUIClips();
        }

        /* Finish Current Activity */
        //appActivity.finish();
    }
}
