package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.Difficulty;
import com.example.germanmemoriserapp.mechanics.Score;
import com.example.germanmemoriserapp.mechanics.ScoreBoardManager;
import com.example.germanmemoriserapp.ui.BackButton;

import java.util.ArrayList;

public class BestScoresScreen extends AppCompatActivity {

    private TextView bestScoreBeginnerScore;
    private TextView bestScoreIntermediateScore;
    private TextView bestScoreMasterScore;

    ArrayList<ArrayList<String>> bestScores;

    BackButton backButton;

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

        /* Setup Back Button */
        backButton = new BackButton(this,this,
                R.id.bestScoresBackBtn,ScoreBoardScreen.class);

        /*Set Scores*/
        setBeginner(beginnerScore);
        setNormal(normalScore);
        setMaster(masterScore);
    }

    private Difficulty getDifficulty(String diffIdStr) {
        int diffId = Integer.parseInt(diffIdStr);
        Difficulty.Level level = Difficulty.getLevel(diffId);

        return new Difficulty(level);
    }

    private String getScoreFromSet(ArrayList<String> set) {
        String score = set.get(ScoreBoardManager.score_token_indx);

        return convertToScoreText(score);
    }

    private String convertToScoreText(String seconds) {
        return String.format("%s Seconds", seconds);
    }

    private void setBeginner(String score) {
        bestScoreBeginnerScore.setText(score);
    }

    private void setNormal(String score) {
        bestScoreIntermediateScore.setText(score);
    }

    private void setMaster(String score) {
        bestScoreMasterScore.setText(score);
    }

    private ArrayList<ArrayList<String>> getScoresFromLoad() {
        Intent loadIntent = getIntent();

        String intentKey = getString(R.string.intent_score_array_key);
        String bundleKey = getString(R.string.bundle_score_array_key);
        Bundle loadedBundle = loadIntent.getBundleExtra(intentKey);

        return (ArrayList) loadedBundle.get(bundleKey);
    }
}
