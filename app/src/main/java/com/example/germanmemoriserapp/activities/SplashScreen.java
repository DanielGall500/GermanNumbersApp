package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;
import com.example.germanmemoriserapp.sound.SoundElement;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.UIClip;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private final Class nextActivity = MenuScreen.class;

    private SoundManager soundManager;
    private OnAudioLoadCompleteHandler audioHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        audioHandler = new OnAudioLoadCompleteHandler(
                this,this, nextActivity);

        /* Load Sounds */
        soundManager = SoundManager.get(this);
        soundManager.setOnAudioLoadedHandler(audioHandler);

        ArrayList<SoundElement> uiSounds = new ArrayList<>();

        uiSounds.add(UIClip.GENERAL_BUTTON_CLICK);

        soundManager.loadAll(uiSounds);
    }

    private class OnAudioLoadCompleteHandler extends Handler {

        NextActivityManager nextActivityManager;
        Context appContext;
        AppCompatActivity appActivity;
        Class nextActivity;

        private final int NUM_UI_CLIPS = 1;
        private int numLoaded = 0;

        public OnAudioLoadCompleteHandler(Context appContext,
                                          AppCompatActivity appActivity,
                                          Class nextActivity) {
            this.appContext = appContext;
            this.appActivity = appActivity;
            this.nextActivity = nextActivity;
        }

        @Override
        public void handleMessage(Message m) {
           numLoaded++;

           if(numLoaded == NUM_UI_CLIPS) {
               nextActivityManager = new NextActivityManager(appContext,appActivity);
               nextActivityManager.setNextActivity(nextActivity);
               nextActivityManager.run();
           }
        }
    }
}
