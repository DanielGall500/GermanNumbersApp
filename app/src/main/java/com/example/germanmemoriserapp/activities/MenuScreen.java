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
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.sound.UIClip;
import com.example.germanmemoriserapp.ui.DifficultyButton;
import com.example.germanmemoriserapp.ui.MenuButton;

public class MenuScreen extends AppCompatActivity {

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

    private DifficultyButton beginnerBtn, normalBtn, masterBtn;

    DifficultyListener difficultyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu_screen);

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

        NewActivityManager playActivityManager = new NewActivityManager(
                this, this, true,
                difficultyListener.getId()
        );

        NewActivityManager learnActivityManager = new NewActivityManager(
                this, this, LearnScreen.class
        );

        NewActivityManager scoresActivityManager = new NewActivityManager(
                this,this, ScoreBoardScreen.class
        );

        new MenuButton(MenuButton.TYPE.PLAY, R.id.menuPlayBtn,
                this,this, playActivityManager);

        new MenuButton(MenuButton.TYPE.LEARN, R.id.menuLearnBtn,
                this,this, learnActivityManager);

        new MenuButton(MenuButton.TYPE.SCORES, R.id.menuScoresBtn,
                this,this, scoresActivityManager);

    }
}



















