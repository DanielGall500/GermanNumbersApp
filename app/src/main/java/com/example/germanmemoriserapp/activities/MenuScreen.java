package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;
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

    private ImageButton playGameBtn, scoreBtn, howToBtn;
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
        howToBtn = findViewById(R.id.menuHowToBtn);

        diffBeginnerBtn = findViewById(R.id.diffBeginnerBtn);
        diffNormalBtn = findViewById(R.id.diffNormalBtn);
        diffMasterBtn = findViewById(R.id.diffMasterBtn);

        difficultyListener = new DifficultyListener(this);

        diffBeginnerBtn.setOnClickListener(difficultyListener);
        diffNormalBtn.setOnClickListener(difficultyListener);
        diffMasterBtn.setOnClickListener(difficultyListener);



        int DIFFICULTY = 0; //TODO
        playGameBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, true,
                DIFFICULTY));


        howToBtn.setOnClickListener(new MoveToNewActivityListener(
                this, this, LearnScreen.class
        ));



        //buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_fly);

        /*
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(v.getId()).startAnimation(buttonAnimation);
            }
        });

        howToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(v.getId()).startAnimation(buttonAnimation);
            }
        });*/
    }
}



















