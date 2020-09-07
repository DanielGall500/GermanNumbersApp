package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.NewActivityManager;
import com.example.germanmemoriserapp.ui.BackButton;

public class ScoreBoardScreen extends AppCompatActivity {

    ImageButton scoresBestBtn;
    ImageButton scoresRecentBtn;

    private final Class loadScreen = LoadScoresScreen.class;

    NewActivityManager newActivity = new NewActivityManager();

    private BackButton backButton;

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

        backButton = new BackButton(this, this, R.id.scoresBackBtn, MenuScreen.class);

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
            String key = appContext.getString(
                    R.string.score_load_intent_key);

            newActivity.move(appContext, appActivity, loadScreen,
                    LoadScoresScreen.BEST_SCORES_ID, key);
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
            String key = appContext.getString(
                    R.string.score_load_intent_key);

            newActivity.move(appContext, appActivity, loadScreen,
                    LoadScoresScreen.RECENT_SCORES_ID, key);
        }
    }
}
