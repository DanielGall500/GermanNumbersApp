package com.example.germanmemoriserapp.activities.scores;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.load.LoadScoresScreen;
import com.example.germanmemoriserapp.activities.menu.MenuScreen;
import com.example.germanmemoriserapp.activity_managers.MenuActivityManager;
import com.example.germanmemoriserapp.ui.buttons.BackButton;
import com.example.germanmemoriserapp.ui.buttons.ScorePageButton;

public class ScoreBoardScreen extends AppCompatActivity {

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

        setContentView(R.layout.activity_score_board_screen);

        new BackButton(this, this,
                R.id.scoresBackBtn, MenuScreen.class);

        new ScorePageButton(this, this, R.id.scoresBestBtn,
                LoadScoresScreen.BEST_SCORES_ID);

        new ScorePageButton(this, this, R.id.scoresRecentBtn,
                LoadScoresScreen.RECENT_SCORES_ID);
    }
}
