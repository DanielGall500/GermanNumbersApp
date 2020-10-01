package ucd.danielgall.klangapp.utilities;

import android.content.Context;
import ucd.danielgall.klangapp.sound.SoundManager;

import androidx.appcompat.app.AppCompatActivity;

public class AppCleanup {

    private Context appContext;
    private AppCompatActivity appActivity;

    public AppCleanup(Context appContext, AppCompatActivity appActivity) {
        this.appContext = appContext;
        this.appActivity = appActivity;
    }

    public void run() {
        /* Release All Audio Clips */
        if (SoundManager.isActive()) {
            SoundManager.get(appContext, appActivity).releaseAllNumberClips();
            SoundManager.get(appContext, appActivity).releaseAllGameUIClips();
        }

        /* Finish Current Activity */
        //appActivity.finish();
    }
}
