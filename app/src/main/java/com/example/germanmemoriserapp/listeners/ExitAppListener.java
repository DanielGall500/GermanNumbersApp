package com.example.germanmemoriserapp.listeners;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ExitAppListener implements View.OnClickListener  {

    AppCompatActivity currentActivity;

    public ExitAppListener(AppCompatActivity act) {
        this.currentActivity = act;
    }

    @Override
    public void onClick(View v) {
        exitApp();
    }

    private void exitApp() {
        currentActivity.finish();
        System.exit(0);
    }

}
