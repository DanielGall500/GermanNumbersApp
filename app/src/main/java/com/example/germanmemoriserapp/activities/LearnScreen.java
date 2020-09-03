package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.listeners.MoveToNewActivityListener;
import com.example.germanmemoriserapp.ui.BackButton;

public class LearnScreen extends AppCompatActivity {

    final int NUM_BTNS = 10;

    int[] buttons = new int[] {
            R.id.zero_to_nine_btn, R.id.ten_to_nineteen_btn,
            R.id.twenty_to_twentynine_btn, R.id.thirty_to_thirtynine_btn,
            R.id.forty_to_fortynine_btn, R.id.fifty_to_fiftynine_btn,
            R.id.sixty_to_sixtynine_btn, R.id.seventy_to_seventynine_btn,
            R.id.eighty_to_eightynine_btn, R.id.ninety_to_ninetynine_btn,
            R.id.hundred_plus_btn
    };

    private BackButton backBtn;
    Animation btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Preference: Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_learn_screen);

        ScrollView learnBtnScrollView = findViewById(R.id.learnBtnScrollView);
        learnBtnScrollView.fullScroll(ScrollView.FOCUS_UP);

        btnAnimation = AnimationUtils.loadAnimation(this, R.anim.button_fly);

        backBtn = new BackButton(this, this,
                R.id.learnScreenBackBtn, MenuScreen.class);

        int N = buttons.length;
        for(int i = 0; i < N; i++) {

            ImageButton btn = findViewById(buttons[i]);

            Listener btnListener = new Listener(this, this, i);

            btn.setOnClickListener(btnListener);
        }
    }

    public class Listener implements View.OnClickListener {

        private int page;
        private Context context;
        private AppCompatActivity activity;

        public Listener(Context context, AppCompatActivity activity, int page) {
            this.context = context;
            this.activity = activity;
            this.page = page;
        }

        @Override
        public void onClick(View v) {
            MoveToNewActivityListener newActivity =
                    new MoveToNewActivityListener();

            newActivity.move(context, activity,
                    false, page);
        }
    }
}
