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
import com.example.germanmemoriserapp.ui.ScorePageButton;

public class ScoreBoardScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_score_board_screen);

        new BackButton(this, this,
                R.id.scoresBackBtn, MenuScreen.class);

        new ScorePageButton(R.id.scoresBestBtn,
                this,this,
                LoadScoresScreen.BEST_SCORES_ID);

        new ScorePageButton(R.id.scoresRecentBtn,
                this,this,
                LoadScoresScreen.RECENT_SCORES_ID);
    }
}
