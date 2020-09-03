package com.example.germanmemoriserapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.audio.SoundManager;
import com.example.germanmemoriserapp.mechanics.NumberFileManager;

public class LearnSelectionScreen extends AppCompatActivity {

    int[] cardIds = new int[] {
            R.id.cardView1, R.id.cardView2,
            R.id.cardView3, R.id.cardView4,
            R.id.cardView5, R.id.cardView6,
            R.id.cardView7, R.id.cardView8,
            R.id.cardView9, R.id.cardView10
    };

    int[] nums = {1,2,3,4,5,6,7,8,9,10};

    final int CARD_RADIUS = 25;
    final String CARD_TEXT_FONT = "baloo";
    final int CARD_TEXT_STYLE = Typeface.BOLD;
    final int CARD_TEXT_SIZE = 25;
    final int CARD_TEXT_COLOUR = Color.BLACK;

    private SoundManager audioManager = SoundManager.get();

    private class MediaListener implements View.OnClickListener {

        private int mediaNumber;

        public MediaListener(int n) {
            super();
            this.mediaNumber = n;
        }

        @Override
        public void onClick(View v) {

            System.out.println("Playing Number: " + mediaNumber);
            audioManager.play(mediaNumber);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Preference: Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_learn_selection_screen);

        NumberFileManager numberManager = new NumberFileManager(
                this,false, true,
                true, true);


        int N = cardIds.length;

        for(int i = 0; i < N; i++) {
            int id = cardIds[i];
            int n = nums[i];

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

            /*
            Set Digit
             */
            digitTxt.setText(String.valueOf(n));

             /*
            Set German
             */
            String germanWord = numberManager.getGermanFromDigit(n);
            germanTxt.setText(germanWord);

            /*
            Set Audio Listener For Play Button
             */
            playBtn.setOnClickListener(new MediaListener(n));


        }

    }

}
