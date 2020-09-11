package com.example.germanmemoriserapp.activity_managers;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class NextActivityManager {

    private Intent activityIntent;
    private Class nextClass;
    private Context appContext;
    private AppCompatActivity appActivity;

    public NextActivityManager(Context context, AppCompatActivity activity) {
        this.appContext = context;
        this.appActivity = activity;
    }

    public void setNextActivity(Class next) {
        this.nextClass = next;
        this.activityIntent = new Intent(appContext, nextClass);
    }

    public Class getNextActivity() {
        return this.nextClass;
    }

    public Context getContext() {
        return this.appContext;
    }

    public void setContext(Context context) {
        this.appContext = context;
    }

    public void addInformation(String key, boolean info) {
        activityIntent.putExtra(key, info);
    }

    public void addInformation(String key, int info) {
        activityIntent.putExtra(key, info);
    }

    public void run() {
        appContext.startActivity(activityIntent);
        appActivity.finish();
    }
}
