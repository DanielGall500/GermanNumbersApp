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

    private TextView bestScoreBeginnerScore;
    private TextView bestScoreIntermediateScore;
    private TextView bestScoreMasterScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_best_scores_screen);

        bestScoreBeginnerScore = findViewById(R.id.bestScoreBeginnerScore);
        bestScoreIntermediateScore = findViewById(R.id.bestScoreIntermediateScore);
        bestScoreMasterScore = findViewById(R.id.bestScoreMasterScore);

        ScoreBoardManager scoreBoardManager = new ScoreBoardManager(this);
        ArrayList<ArrayList<String>> scores = scoreBoardManager.getBestScores();

        ArrayList<String> beginnerArr, normalArr, masterArr;
        beginnerArr = scores.get(0);
        normalArr = scores.get(1);
        masterArr = scores.get(2);

        String beginnerScore = beginnerArr.get(1);
        String normalScore = normalArr.get(1);
        String masterScore = masterArr.get(1);

        bestScoreBeginnerScore.setText(beginnerScore);
        bestScoreIntermediateScore.setText(normalScore);
        bestScoreMasterScore.setText(masterScore);

    }
}
