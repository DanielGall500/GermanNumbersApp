package com.example.germanmemoriserapp.activities.learn;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.menu.MenuScreen;
import com.example.germanmemoriserapp.sound.SoundManager;
import com.example.germanmemoriserapp.ui.buttons.BackButton;
import com.example.germanmemoriserapp.ui.buttons.LearnPageButton;

public class LearnScreen extends AppCompatActivity {

    final int NUM_BTNS = 10;

    int[] buttons = new int[]{
            R.id.learn_zero_to_nine_btn, R.id.learn_ten_to_nineteen_btn,
            R.id.learn_twenty_to_twenty_nine_btn, R.id.learn_thirty_to_thirty_nine_btn,
            R.id.learn_forty_to_forty_nine_btn, R.id.learn_fifty_to_fifty_nine_btn,
            R.id.learn_sixty_to_sixty_nine_btn, R.id.learn_seventy_to_seventy_nine_btn,
            R.id.learn_eighty_to_eighty_nine_btn, R.id.learn_ninety_to_ninety_nine_btn,
    };

    SoundManager soundManager;

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

        new BackButton(this, this,
                R.id.learnScreenBackBtn, MenuScreen.class);

        int N = buttons.length;

        for (int i = 0; i < N; i++) {
            new LearnPageButton(this, this, buttons[i], i);
        }

        /* We need to ensure everything unnecessary is released
         * before we begin loading new numbers. */
        soundManager = SoundManager.get(this);
        soundManager.releaseAllNumberClips();
    }
}
