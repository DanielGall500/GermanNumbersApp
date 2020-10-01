package ucd.danielgall.klangapp.activities.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;
import ucd.danielgall.klangapp.sound.SoundManager;
import ucd.danielgall.klangapp.sound.elements.SoundElement;
import ucd.danielgall.klangapp.sound.elements.UIClip;

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
                this, this, nextActivity);

        /* Load Sounds */
        soundManager = SoundManager.get(this, this);
        soundManager.setOnAudioLoadedHandler(audioHandler);

        ArrayList<SoundElement> uiSounds = new ArrayList<>();

        uiSounds.add(UIClip.GENERAL_BUTTON_CLICK);
        uiSounds.add(UIClip.BACK_BUTTON_CLICK);

        soundManager.loadAll(uiSounds);
    }

    private class OnAudioLoadCompleteHandler extends Handler {

        private final int NUM_UI_CLIPS = 1;
        NextActivityManager nextActivityManager;
        Context appContext;
        AppCompatActivity appActivity;
        Class nextActivity;
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

            if (numLoaded == NUM_UI_CLIPS) {
                nextActivityManager = new NextActivityManager(appContext, appActivity);
                nextActivityManager.setNextActivity(nextActivity);
                nextActivityManager.run();
            }
        }
    }
}
