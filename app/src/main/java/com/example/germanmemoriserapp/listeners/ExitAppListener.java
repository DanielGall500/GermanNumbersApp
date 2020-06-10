package com.example.germanmemoriserapp.listeners;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
