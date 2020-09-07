package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.ScoreBoardManager;
import com.example.germanmemoriserapp.ui.BackButton;

import java.util.ArrayList;

public class RecentScoresScreen extends AppCompatActivity {

    private int[] cardIds = new int[] {
            R.id.recentScoresCard1, R.id.recentScoresCard2,
            R.id.recentScoresCard3, R.id.recentScoresCard4,
            R.id.recentScoresCard5, R.id.recentScoresCard6,
            R.id.recentScoresCard7, R.id.recentScoresCard8,
            R.id.recentScoresCard9, R.id.recentScoresCard10
    };

    private final int CARD_RADIUS = 25;
    private final String CARD_TEXT_FONT = "baloo";
    private final int CARD_TEXT_STYLE = Typeface.BOLD;
    private final int CARD_TEXT_SIZE = 25;
    private final int CARD_TEXT_COLOUR = Color.BLACK;

    private ArrayList<ArrayList<String>> recentScores;

    private BackButton backButton;

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

        for(int i = 0; i < N; i++) {

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
            Set Text
             */
            ArrayList<String> nextScoreSet = recentScores.get(i);
            String nextDiff = getDifficultyFromSet(nextScoreSet);
            String nextScore = getScoreFromSet(nextScoreSet);

            diffTxt.setText(nextDiff);
            scoreTxt.setText(nextScore);
        }

    }

    private ArrayList<ArrayList<String>> getScoresFromLoad() {
        Intent loadIntent = getIntent();

        String intentKey = getString(R.string.intent_score_array_key);
        String bundleKey = getString(R.string.bundle_score_array_key);
        Bundle loadedBundle = loadIntent.getBundleExtra(intentKey);

        return (ArrayList) loadedBundle.get(bundleKey);
    }

    private String getDifficultyFromSet(ArrayList<String> set) {
        return set.get(ScoreBoardManager.difficulty_token_indx);
    }

    private String getScoreFromSet(ArrayList<String> set) {
        return set.get(ScoreBoardManager.score_token_indx);
    }
}
