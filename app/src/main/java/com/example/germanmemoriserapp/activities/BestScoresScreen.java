package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.Score;
import com.example.germanmemoriserapp.mechanics.ScoreBoardManager;

import java.util.ArrayList;

public class BestScoresScreen extends AppCompatActivity {

    private TextView bestScoreBeginnerTxt;
    private TextView bestScoreIntermediateTxt;
    private TextView bestScoreMasterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_best_scores_screen);

        bestScoreBeginnerTxt = findViewById(R.id.bestScoreBeginnerTxt);
        bestScoreIntermediateTxt = findViewById(R.id.bestScoreIntermediateTxt);
        bestScoreMasterTxt = findViewById(R.id.bestScoreMasterTxt);

        ScoreBoardManager scoreBoardManager = new ScoreBoardManager(this);
        ArrayList<String> scores = scoreBoardManager.getBestScores();
    }
}
