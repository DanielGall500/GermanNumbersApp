package com.example.germanmemoriserapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MoveToNewActivityListener implements View.OnClickListener {

    Context currentContext;
    AppCompatActivity currentActivity;

    Intent moveToNewScreen;

    public MoveToNewActivityListener(Context currContext,
                                     AppCompatActivity currActivity,
                                     Class newActivity)
    {
        this.currentContext = currContext;
        this.currentActivity = currActivity;

        moveToNewScreen = new Intent(currActivity, newActivity);
    }

    @Override
    public void onClick(View v)
    {
        currentContext.startActivity(moveToNewScreen);
        currentActivity.finish();
    }
}
