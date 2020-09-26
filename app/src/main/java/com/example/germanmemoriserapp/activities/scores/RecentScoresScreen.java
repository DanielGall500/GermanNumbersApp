package com.example.germanmemoriserapp.activities.scores;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.MenuActivityManager;
import com.example.germanmemoriserapp.mechanics.game.Difficulty;
import com.example.germanmemoriserapp.mechanics.score.ScoreBoardManager;
import com.example.germanmemoriserapp.ui.buttons.BackButton;

import java.util.ArrayList;

public class RecentScoresScreen extends AppCompatActivity {

    private final int CARD_RADIUS = 25;
    private final String CARD_TEXT_FONT = "baloo";
    private final int CARD_TEXT_STYLE = Typeface.BOLD;
    private final int CARD_TEXT_SIZE = 25;
    private final int CARD_TEXT_COLOUR = Color.BLACK;
    private final String EMPTY_TXT = "";
    private int[] cardIds = new int[]{
            R.id.recentScoresCard1, R.id.recentScoresCard2,
            R.id.recentScoresCard3, R.id.recentScoresCard4,
            R.id.recentScoresCard5, R.id.recentScoresCard6,
            R.id.recentScoresCard7, R.id.recentScoresCard8,
            R.id.recentScoresCard9, R.id.recentScoresCard10
    };
    private ArrayList<ArrayList<String>> recentScores;

    private BackButton backButton;

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

        setContentView(R.layout.activity_recent_scores_screen);

        recentScores = getScoresFromLoad();

        backButton = new BackButton(this, this, R.id.bestScoresBackBtn, ScoreBoardScreen.class);

        int N = cardIds.length;

        for (int i = 0; i < N; i++) {

            int id = cardIds[i];
            CardView card = findViewById(id);

            card.setRadius(CARD_RADIUS);

            ConstraintLayout cardContainer = (ConstraintLayout) card.getChildAt(0);

            TextView diffTxt = (TextView) cardContainer.getChildAt(0);
            TextView scoreTxt = (TextView) cardContainer.getChildAt(1);

            /*
            Set Font & Style
             */
            diffTxt.setTypeface(Typeface.create(CARD_TEXT_FONT, CARD_TEXT_STYLE));
            scoreTxt.setTypeface(Typeface.create(CARD_TEXT_FONT, CARD_TEXT_STYLE));

            /*
            Set Text Size
             */
            diffTxt.setTextSize(CARD_TEXT_SIZE);
            scoreTxt.setTextSize(CARD_TEXT_SIZE);

            /*
            Set Text Colour
             */
            diffTxt.setTextColor(CARD_TEXT_COLOUR);
            scoreTxt.setTextColor(CARD_TEXT_COLOUR);

            /*
            Retrieve Level/Score & Set Text
             */
            ArrayList<String> nextScoreSet = recentScores.get(i);

            Difficulty nextDiff = getDifficultyFromSet(nextScoreSet);
            diffTxt.setText(nextDiff.toString());

            // If our slot is not empty, fill our score
            if (!nextDiff.getLevel().equals(Difficulty.Level.NONE)) {
                String nextScore = getScoreFromSet(nextScoreSet);
                scoreTxt.setText(nextScore);
            } else {
                scoreTxt.setText(EMPTY_TXT);
            }
        }

    }

    private ArrayList<ArrayList<String>> getScoresFromLoad() {
        Intent loadIntent = getIntent();

        String intentKey = getString(R.string.intent_score_array_key);
        String bundleKey = getString(R.string.bundle_score_array_key);
        Bundle loadedBundle = loadIntent.getBundleExtra(intentKey);

        return (ArrayList) loadedBundle.get(bundleKey);
    }

    private Difficulty getDifficultyFromSet(ArrayList<String> set) {
        String diffIdStr = set.get(ScoreBoardManager.difficulty_token_indx);
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
}
