package ucd.danielgall.klangapp.activities.scores;

import ucd.danielgall.klangapp.activities.menu.MenuScreen;
import ucd.danielgall.klangapp.activity_managers.MenuActivityManager;
import ucd.danielgall.klangapp.ui.buttons.BackButton;
import ucd.danielgall.klangapp.ui.buttons.ScorePageButton;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.activities.load.LoadScoresScreen;

public class ScoreBoardScreen extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        MenuActivityManager menuManager = new MenuActivityManager(this, this);
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
