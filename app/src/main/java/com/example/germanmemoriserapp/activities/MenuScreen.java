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
import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;
import com.example.germanmemoriserapp.mechanics.Difficulty;

public class MenuScreen extends AppCompatActivity {

    private ImageButton playGameBtn, scoreBtn, learnbtn;
    private ImageButton diffBeginnerBtn, diffNormalBtn, diffMasterBtn;

    private final Difficulty.Level INITIAL_DIFFICULTY = Difficulty.Level.NORMAL;

    private int[] difficultyBtns = new int[] {
            R.id.diffBeginnerBtn,
            R.id.diffNormalBtn,
            R.id.diffMasterBtn
    };

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

        diffBeginnerBtn = findViewById(R.id.diffBeginnerBtn);
        diffNormalBtn = findViewById(R.id.diffNormalBtn);
        diffMasterBtn = findViewById(R.id.diffMasterBtn);

        difficultyListener = new DifficultyListener(this);

        diffBeginnerBtn.setOnClickListener(difficultyListener);
        diffNormalBtn.setOnClickListener(difficultyListener);
        diffMasterBtn.setOnClickListener(difficultyListener);



        int DIFFICULTY = 0; //TODO
        MoveToNewActivityListener moveListener = new MoveToNewActivityListener();
        playGameBtn.setOnClickListener(new PlayListener(this, this, DIFFICULTY));
        
        learnbtn.setOnClickListener(new LearnListener(this, this));

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_fly);
    }

    public class LearnListener implements View.OnClickListener {

        Context context;
        AppCompatActivity activity;

        public LearnListener(Context context, AppCompatActivity activity) {
            this.context = context;
            this.activity = activity;
        }


        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);

            MoveToNewActivityListener moveListener = new MoveToNewActivityListener();
            moveListener.move(context, activity, LearnScreen.class);
        }
    }

    public class PlayListener implements View.OnClickListener {

        Context context;
        AppCompatActivity activity;
        int difficulty;

        public PlayListener(Context context, AppCompatActivity activity, int difficulty) {
            this.context = context;
            this.activity = activity;
            this.difficulty = difficulty;
        }

        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);

            MoveToNewActivityListener moveListener = new MoveToNewActivityListener();
            moveListener.move(context, activity, true, difficulty);
        }
    }

    public class ScoreListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            findViewById(v.getId()).startAnimation(buttonAnimation);
        }
    }
}



















