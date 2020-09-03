package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.NumberFileManager;

public class LearnSelectionScreen extends AppCompatActivity {

    int[] cardIds = new int[] {
            R.id.cardView1, R.id.cardView2,
            R.id.cardView3, R.id.cardView4,
            R.id.cardView5, R.id.cardView6,
            R.id.cardView7, R.id.cardView8,
            R.id.cardView9, R.id.cardView10
    };

    final int CARD_RADIUS = 25;
    final String CARD_TEXT_FONT = "baloo";
    final int CARD_TEXT_STYLE = Typeface.BOLD;
    final int CARD_TEXT_SIZE = 25;
    final int CARD_TEXT_COLOUR = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Preference: Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_learn_selection_screen);

        for(int id : cardIds) {
            CardView card = findViewById(id);

            card.setRadius(CARD_RADIUS);

            ConstraintLayout cardContainer = (ConstraintLayout) card.getChildAt(0);

            ImageButton playBtn = (ImageButton) cardContainer.getChildAt(0);
            TextView germanTxt = (TextView) cardContainer.getChildAt(1);
            TextView digitTxt = (TextView) cardContainer.getChildAt(2);

            /*
            Set Font & Style
             */
            germanTxt.setTypeface(Typeface.create(CARD_TEXT_FONT, CARD_TEXT_STYLE));
            digitTxt.setTypeface(Typeface.create(CARD_TEXT_FONT, CARD_TEXT_STYLE));

            /*
            Set Text Size
             */
            germanTxt.setTextSize(CARD_TEXT_SIZE);
            digitTxt.setTextSize(CARD_TEXT_SIZE);

            /*
            Set Text Colour
             */
            germanTxt.setTextColor(CARD_TEXT_COLOUR);
            digitTxt.setTextColor(CARD_TEXT_COLOUR);


        }

    }






    }
