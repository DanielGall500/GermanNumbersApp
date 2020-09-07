package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.DifficultyListener;
import com.example.germanmemoriserapp.listeners.NewActivityManager;
import com.example.germanmemoriserapp.mechanics.Difficulty;
import com.example.germanmemoriserapp.ui.DifficultyButton;

public class MenuScreen extends AppCompatActivity {

    /*
    TODO

    - Losing the game
    - Sound effects
    - Polish UI
    - Setup 100-1000
     */

    private ImageButton playGameBtn, scoreBtn, learnbtn;

    private final boolean INITIAL_BEGINNER_IS_PRESSED = false;
    private final boolean INITIAL_NORMAL_IS_PRESSED = true;
    private final boolean INITIAL_MASTER_IS_PRESSED = false;

    private final Difficulty.Level INITIAL_DIFFICULTY = Difficulty.Level.NORMAL;

    private int[] difficultyBtnIds = new int[] {
            R.id.diffBeginnerBtn,
            R.id.diffNormalBtn,
            R.id.diffMasterBtn
    };

    private int[] unpressedImgIds = new int[] {
            R.drawable.beginner_btn,
            R.drawable.normal_btn,
            R.drawable.master_btn
    };

    private int[] pressedImgIds = new int[] {
            R.drawable.beginner_btn_pressed,
            R.drawable.normal_btn_pressed,
            R.drawable.master_btn_pressed
    };

    private final int beginnerId = Difficulty.getId(Difficulty.Level.BEGINNER);
    private final int normalId = Difficulty.getId(Difficulty.Level.NORMAL);
    private final int masterId = Difficulty.getId(Difficulty.Level.MASTER);

    DifficultyButton beginnerBtn, normalBtn, masterBtn;

    DifficultyListener difficultyListener;

    Animation buttonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu_screen);

        playGameBtn = findViewById(R.id.menuPlayBtn);
        scoreBtn = findViewById(R.id.menuScoresBtn);
        learnbtn = findViewById(R.id.menuLearnBtn);


        beginnerBtn = new DifficultyButton(
                this, difficultyBtnIds[beginnerId],
                unpressedImgIds[beginnerId], pressedImgIds[beginnerId],
                beginnerId,INITIAL_BEGINNER_IS_PRESSED);

        normalBtn = new DifficultyButton(
                this, difficultyBtnIds[normalId],
                unpressedImgIds[normalId], pressedImgIds[normalId],
                normalId,INITIAL_NORMAL_IS_PRESSED);

        masterBtn = new DifficultyButton(
                this, difficultyBtnIds[masterId],
                unpressedImgIds[masterId], pressedImgIds[masterId],
                masterId,INITIAL_MASTER_IS_PRESSED);

        difficultyListener = new DifficultyListener(beginnerBtn,normalBtn,
                masterBtn,Difficulty.getId(INITIAL_DIFFICULTY));

        beginnerBtn.setListener(difficultyListener);
        normalBtn.setListener(difficultyListener);
        masterBtn.setListener(difficultyListener);

        playGameBtn.setOnClickListener(new PlayListener(this, this,
                difficultyListener.getId()));
        
        learnbtn.setOnClickListener(new LearnListener(this, this));

        scoreBtn.setOnClickListener(new ScoreListener(this,this));

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_fly);
    }

    public class LearnListener implements View.OnClickListener {

        private Context appContext;
        AppCompatActivity appActivity;

        public LearnListener(Context context, AppCompatActivity activity) {
            this.appContext = context;
            this.appActivity = activity;
        }


        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);

            NewActivityManager moveListener = new NewActivityManager();
            moveListener.move(appContext, appActivity, LearnScreen.class);
        }
    }

    public class PlayListener implements View.OnClickListener {

        private Context appContext;
        AppCompatActivity appActivity;

        public PlayListener(Context context, AppCompatActivity activity, int difficulty) {
            this.appContext = context;
            this.appActivity = activity;
        }

        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);

            NewActivityManager moveListener = new NewActivityManager();
            moveListener.move(appContext, appActivity, true,
                    difficultyListener.getId());
        }
    }

    public class ScoreListener implements View.OnClickListener {

        private Context appContext;
        private AppCompatActivity appActivity;

        public ScoreListener(Context context, AppCompatActivity activity) {
            this.appContext = context;
            this.appActivity = activity;
        }

        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);

            NewActivityManager moveListener = new NewActivityManager();
            moveListener.move(appContext, appActivity, ScoreBoardScreen.class);
        }
    }
}



















