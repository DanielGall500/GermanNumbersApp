package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.germanmemoriserapp.sound.SoundElement;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.UIClip;
import com.example.germanmemoriserapp.ui.DifficultyButton;

import java.util.ArrayList;

public class MenuScreen extends AppCompatActivity {

    /*
    TODO
    - Sound effects
    - Polish UI
    - Setup 100-1000
     */

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

    private int buttonAnimationId = R.anim.fly_right;

    private final int beginnerId = Difficulty.getId(Difficulty.Level.BEGINNER);
    private final int normalId = Difficulty.getId(Difficulty.Level.NORMAL);
    private final int masterId = Difficulty.getId(Difficulty.Level.MASTER);

    private DifficultyButton beginnerBtn, normalBtn, masterBtn;
    private ImageButton playGameBtn, scoreBtn, learnBtn;

    private SoundManager soundManager;

    DifficultyListener difficultyListener;

    Animation buttonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);


        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu_screen);

        soundManager = SoundManager.get(this);

        playGameBtn = findViewById(R.id.menuPlayBtn);
        scoreBtn = findViewById(R.id.menuScoresBtn);
        learnBtn = findViewById(R.id.menuLearnBtn);

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
        
        learnBtn.setOnClickListener(new LearnListener(this, this));

        scoreBtn.setOnClickListener(new ScoreListener(this,this));

        /* Choose The Animation For Our Buttons */
        buttonAnimation = AnimationUtils.loadAnimation(this, buttonAnimationId);
        buttonAnimation.setAnimationListener(new OnAnimListener());

    }

    private final int gameButtonRef = 0;
    private final int learnButtonRef = 1;
    private final int scoreButtonRef = 2;

    private int clickedBtn;
    boolean buttonClicked = false;

    public class OnAnimListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            switch (clickedBtn) {
                case gameButtonRef:
                    playGameBtn.setVisibility(View.INVISIBLE);
                    onMovingToGame();
                    break;
                case learnButtonRef:
                    learnBtn.setVisibility(View.INVISIBLE);
                    onMovingToLearn();
                    break;
                case scoreButtonRef:
                    scoreBtn.setVisibility(View.INVISIBLE);
                    onMovingToScoreBoard();
                    break;
                default:
                    throw new IllegalArgumentException("invalid Reference");
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
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

            if(buttonClicked)
                return;

            //Play Audio
            soundManager.play(UIClip.GENERAL_BUTTON_CLICK);

            clickedBtn = learnButtonRef;
            buttonClicked = true;
            findViewById(v.getId()).startAnimation(buttonAnimation);
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

            if(buttonClicked)
                return;

            //Play Audio
            soundManager.play(UIClip.GENERAL_BUTTON_CLICK);


            clickedBtn = gameButtonRef;
            buttonClicked = true;
            findViewById(v.getId()).startAnimation(buttonAnimation);
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

            if(buttonClicked)
                return;

            //Play Audio
            soundManager.play(UIClip.GENERAL_BUTTON_CLICK);

            clickedBtn = scoreButtonRef;
            buttonClicked = true;
            findViewById(v.getId()).startAnimation(buttonAnimation);
        }
    }

    /* Call On Moving To A New Activity */
    NewActivityManager moveListener = new NewActivityManager();

    private void onMovingToGame() {
        moveListener.move(this, this, true,
                difficultyListener.getId());
    }

    private void onMovingToLearn() {
        moveListener.move(this, this, LearnScreen.class);
    }

    private void onMovingToScoreBoard() {
        moveListener.move(this,this, ScoreBoardScreen.class);
    }
}



















