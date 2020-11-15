package ucd.danielgall.klangapp.utilities;

import android.content.Context;

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
        /* Finish Current Activity */
    }
}
