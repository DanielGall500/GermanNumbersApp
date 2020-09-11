package com.example.germanmemoriserapp.activities.load;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.scores.BestScoresScreen;
import com.example.germanmemoriserapp.activities.scores.RecentScoresScreen;
import com.example.germanmemoriserapp.mechanics.score.ScoreBoardManager;

import java.util.ArrayList;

public class LoadScoresScreen extends AppCompatActivity {

    public static final int BEST_SCORES_ID = 0;
    public static final int RECENT_SCORES_ID = 1;
    private final int WAIT_TIME = 1000;
    Intent nextScreen;
    private AnimationDrawable loadBtnAnim;
    private ImageView loadingScoresBuffer;
    private int currentIntent = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_load_scores_screen);

        loadingScoresBuffer = findViewById(R.id.loadingScoresBuffer);
        loadingScoresBuffer.setBackgroundResource(R.drawable.loading_screen_buffer);
        loadBtnAnim = (AnimationDrawable) loadingScoresBuffer.getBackground();
        loadBtnAnim.start();

        Runnable loadScoresRunnable = new Runnable() {
            @Override
            public void run() {
                /* Find Which Screen User Wants */
                currentIntent = loadIntentInfo();
                setNextIntent(currentIntent);

                /* Open File & Load */
                loadScores();

                /* Move To Loaded Activity */
                startActivity(nextScreen);
                finish();
            }
        };

        Handler loadScoresHandler = new Handler();
        loadScoresHandler.postDelayed(loadScoresRunnable, WAIT_TIME);
    }

    private boolean intentIsValid(int intent) {
        return (intent == BEST_SCORES_ID) ||
                (intent == RECENT_SCORES_ID);
    }

    private boolean isBestScores(int intent) {
        return (intent == BEST_SCORES_ID);
    }

    private boolean isRecentScores(int intent) {
        return (intent == RECENT_SCORES_ID);
    }

    private void setNextIntent(int intentId) {
        if (isBestScores(intentId)) {
            nextScreen = new Intent(this, BestScoresScreen.class);
        } else if (isRecentScores(intentId)) {
            nextScreen = new Intent(this, RecentScoresScreen.class);
        } else {
            throw new IllegalStateException("Invalid Intent");
        }
    }

    private int loadIntentInfo() {
        Intent fromPrevious = getIntent();

        //FIX THESE KEYS
        String key = getString(R.string.score_load_intent_key);

        int info = fromPrevious.getIntExtra(key, -1);

        /*
        Tells the loading screen whether to load
        our best scores, or our most recent scores.

        This is decided by a special id
        specified at the beginning of this class.
         */
        if (intentIsValid(info)) {
            return info;
        } else {
            throw new IllegalStateException("Invalid State Passed");
        }
    }

    private void loadScores() {

        //PUT ON NEW THREAD
        ScoreBoardManager manager = new ScoreBoardManager(this);

        ArrayList<ArrayList<String>> loadedScores;

        if (isBestScores(currentIntent)) {
            loadedScores = manager.getBestScores();
        } else if (isRecentScores(currentIntent)) {
            loadedScores = manager.getRecentScores();
        } else {
            throw new IllegalStateException("Invalid Intent Passed");
        }

        Bundle bundle = new Bundle();
        String bundleKey = getString(R.string.bundle_score_array_key);
        bundle.putSerializable(bundleKey, loadedScores);

        String intentKey = getString(R.string.intent_score_array_key);
        nextScreen.putExtra(intentKey, bundle);
    }
}
