package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    ArrayList<ArrayList<String>> bestScores;

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

        bestScores = getScoresFromLoad();

        /* Retrieve Scores */
        ArrayList<String> beginnerSet = bestScores.get(
                ScoreBoardManager.beginner_token_indx);

        String beginnerScore = getScoreFromSet(beginnerSet);

        ArrayList<String> normalSet = bestScores.get(
                ScoreBoardManager.normal_token_indx);

        String normalScore = getScoreFromSet(normalSet);

        ArrayList<String> masterSet = bestScores.get(
                ScoreBoardManager.master_token_indx);

        String masterScore = getScoreFromSet(masterSet);

        /*Set Scores*/
        setBeginner(beginnerScore);
        setNormal(normalScore);
        setMaster(masterScore);
    }

    public String getScoreFromSet(ArrayList<String> set) {
        return set.get(ScoreBoardManager.score_token_indx);
    }

    public void setBeginner(String score) {
        bestScoreBeginnerScore.setText(score);
    }

    public void setNormal(String score) {
        bestScoreIntermediateScore.setText(score);
    }

    public void setMaster(String score) {
        bestScoreMasterScore.setText(score);
    }

    public ArrayList<ArrayList<String>> getScoresFromLoad() {
        Intent loadIntent = getIntent();

        String intentKey = getString(R.string.intent_score_array_key);
        String bundleKey = getString(R.string.bundle_score_array_key);
        Bundle loadedBundle = loadIntent.getBundleExtra(intentKey);

        return (ArrayList) loadedBundle.get(bundleKey);
    }
}
