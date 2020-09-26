package com.example.germanmemoriserapp.activities.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.MenuActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Difficulty;
import com.example.germanmemoriserapp.mechanics.score.ScoreBoardManager;
import com.example.germanmemoriserapp.ui.buttons.BackButton;
import com.example.germanmemoriserapp.utilities.AppCleanup;

import java.util.ArrayList;

public class BestScoresScreen extends AppCompatActivity {

    ArrayList<ArrayList<String>> bestScores;
    BackButton backButton;
    private TextView bestScoreBeginnerScore;
    private TextView bestScoreIntermediateScore;
    private TextView bestScoreMasterScore;

    private String NEVER_PLAYED = "None Yet!";

    @Override
    public void onBackPressed() {
        MenuActivityManager menuManager = new MenuActivityManager(this,this);
        menuManager.run();
    }

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

        /* Setup Back Button */
        backButton = new BackButton(this, this,
                R.id.bestScoresBackBtn, ScoreBoardScreen.class);

        /* Retrieve Scores */
        ArrayList<String> beginnerSet = bestScores.get(
                ScoreBoardManager.beginner_token_indx);

        ArrayList<String> normalSet = bestScores.get(
                ScoreBoardManager.normal_token_indx);

        ArrayList<String> masterSet = bestScores.get(
                ScoreBoardManager.master_token_indx);

        /* Convert To Game Text */
        String beginnerScore = getScoreFromSet(beginnerSet);
        String normalScore = getScoreFromSet(normalSet);
        String masterScore = getScoreFromSet(masterSet);

        /* Set Score s*/
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

        if(Integer.parseInt(score) == -1) {
            return NEVER_PLAYED;
        }

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
