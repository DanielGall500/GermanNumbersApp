package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.NewActivityManager;

public class ScoreBoardScreen extends AppCompatActivity {

    ImageButton scoresBestBtn;
    ImageButton scoresRecentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_score_board_screen);

        scoresBestBtn = findViewById(R.id.scoresBestBtn);
        scoresRecentBtn = findViewById(R.id.scoresRecentBtn);

        scoresBestBtn.setOnClickListener(new BestButtonListener(this, this));
        scoresRecentBtn.setOnClickListener(new RecentButtonListener(this, this));
    }

    private class BestButtonListener implements View.OnClickListener {

        private Context appContext;
        private AppCompatActivity appActivity;

        public BestButtonListener(Context context, AppCompatActivity activity) {
            this.appContext = context;
            this.appActivity = activity;
        }

        @Override
        public void onClick(View v) {
            NewActivityManager newActivity = new NewActivityManager();
            newActivity.move(appContext, appActivity, BestScoresScreen.class);
        }
    }

    private class RecentButtonListener implements View.OnClickListener {

        private Context appContext;
        private AppCompatActivity appActivity;

        public RecentButtonListener(Context context, AppCompatActivity activity) {
            this.appContext = context;
            this.appActivity = activity;
        }

        @Override
        public void onClick(View v) {
            NewActivityManager newActivity = new NewActivityManager();
            newActivity.move(appContext, appActivity, RecentScoresScreen.class);
        }
    }
}
