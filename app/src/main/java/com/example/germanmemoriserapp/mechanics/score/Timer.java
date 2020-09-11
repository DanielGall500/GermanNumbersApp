package com.example.germanmemoriserapp.mechanics.score;

import android.os.Handler;
import android.widget.TextView;

public class Timer {

    private Handler timeHandler = new Handler();

    private TextView timerView;
    private long startTime;

    private int savedTime = -1;
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = getCurrentTime(startTime);

            savedTime = millisToSeconds(millis);

            timerView.setText(String.valueOf(savedTime));

            timeHandler.postDelayed(this, 500);
        }
    };

    public Timer(TextView view) {
        timerView = view;
    }

    public void begin() {
        startTime = System.currentTimeMillis();

        timeHandler.postDelayed(timerRunnable, 0);
    }

    public void stop() {
        timeHandler.removeCallbacks(timerRunnable);
    }

    public int getPreviousResult() {
        if (savedTime == -1)
            throw new RuntimeException("No Previous Result Saved");

        return savedTime;
    }

    private long getCurrentTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private int millisToSeconds(long millis) {
        return (int) (millis / 1000);
    }
}
