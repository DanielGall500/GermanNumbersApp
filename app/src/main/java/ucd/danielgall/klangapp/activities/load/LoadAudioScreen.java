package ucd.danielgall.klangapp.activities.load;

import android.content.Intent;
import ucd.danielgall.klangapp.activities.game.GameScreen;
import ucd.danielgall.klangapp.activities.learn.LearnSelectionScreen;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;
import ucd.danielgall.klangapp.sound.elements.SoundElement;
import ucd.danielgall.klangapp.utilities.NumberGenerator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;

import ucd.danielgall.klangapp.mechanics.game.Game;

import java.util.ArrayList;

public class LoadAudioScreen extends AppCompatActivity {

    NextActivityManager moveToNextScreen;
    NumberGenerator generator = new NumberGenerator();
    ArrayList<SoundElement> generatedArr;
    private ProgressBar audioProgressBar;
    private AnimationDrawable loadBtnAnim;
    private ImageView loadBtn;
    private int NUMBER_CLIPS = Game.NUMBER_CLIPS;
    private int UI_CLIPS = Game.UI_CLIPS;
    private int TOTAL_CLIPS = NUMBER_CLIPS + UI_CLIPS;
    private int LEARN_PAGE_CLIPS = 10;

    /*
    Load Screen

    Can Load:
    - The Game
        - Input: isGame, Difficulty
        - Output: Loaded Audio

    - Learning Activities
        - Input: isGame, Page Number
        - Output: Loaded Audio, German String Arr, Digit String Arr
     */
    private int audioProgress = 0;
    private boolean isGame;
    private int loadInformation;

    public Class getNextScreen(boolean isGame) {
        if (isGame) {
            return GameScreen.class;
        } else {
            return LearnSelectionScreen.class;
        }
    }

    private void retrieveLoadScreenInput() {
        Intent inputToLoadScreen = getIntent();

        /*
        This key tells us if we're loading a game or a learning page
         */
        final String isGameKey = getString(R.string.load_screen_isGameBoolean);
        isGame = inputToLoadScreen.getBooleanExtra(
                isGameKey, true);

        /*
        Depending on if we are loading a game or a learning page,
        this key will tell us either the difficulty level or the page
        to load.
         */
        final String infoKey = getString(R.string.load_screen_information);
        loadInformation = inputToLoadScreen.getIntExtra(infoKey, 1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_load_screen);

        loadBtn = findViewById(R.id.loadBtn);
        loadBtnAnim = (AnimationDrawable) loadBtn.getDrawable();
        loadBtnAnim.start();

        retrieveLoadScreenInput();
        loadComplete(getNextScreen(isGame));
    }

    private void loadComplete(Class nextScreen) {
        String key = getString(R.string.load_screen_information);

        moveToNextScreen = new NextActivityManager(this, this);
        moveToNextScreen.setNextActivity(nextScreen);
        moveToNextScreen.addInformation(key, loadInformation);
        moveToNextScreen.run();
    }
}
