package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.NumberFileManager;

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

        NumberFileManager manager = new NumberFileManager(this);
        manager.print();

    }
}
