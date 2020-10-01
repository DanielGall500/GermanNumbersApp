package ucd.danielgall.klangapp.activities.learn;

import android.content.Intent;
import ucd.danielgall.klangapp.utilities.LearnPage;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.activity_managers.MenuActivityManager;
import ucd.danielgall.klangapp.sound.SoundManager;
import ucd.danielgall.klangapp.ui.buttons.BackButton;
import ucd.danielgall.klangapp.utilities.AppCleanup;
import ucd.danielgall.klangapp.utilities.NumberFileManager;

public class LearnSelectionScreen extends AppCompatActivity {

    final int CARD_RADIUS = 25;
    final String CARD_TEXT_FONT = "baloo";
    final int CARD_TEXT_STYLE = Typeface.BOLD;
    final int CARD_TEXT_SIZE = 25;
    final int CARD_TEXT_COLOUR = Color.BLACK;

    int[] cardIds = new int[]{
            R.id.cardView1, R.id.cardView2,
            R.id.cardView3, R.id.cardView4,
            R.id.cardView5, R.id.cardView6,
            R.id.cardView7, R.id.cardView8,
            R.id.cardView9, R.id.cardView10
    };
    private int loadScreenInformation;
    private int minNumber;
    private int maxNumber;

    private BackButton learnScreenSelectedBackBtn;

    private SoundManager audioManager = SoundManager.get(this, this);

    public void retrieveInformationFromLoadScreen() {
        Intent fromLoadScreen = getIntent();
        String key = getString(R.string.load_screen_information);
        loadScreenInformation = fromLoadScreen.getIntExtra(key, -1);

        int[] minMax = LearnPage.getMinMax(loadScreenInformation);
        minNumber = minMax[0];
        maxNumber = minMax[1];
    }

    @Override
    public void onBackPressed() {
        AppCleanup cleaner = new AppCleanup(this, this);
        MenuActivityManager menuManager = new MenuActivityManager(this, this);

        cleaner.run();
        menuManager.run();
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

        learnScreenSelectedBackBtn = new BackButton(this, this,
                R.id.learnScreenSelectedBackBtn, LearnScreen.class);

        NumberFileManager numberManager = new NumberFileManager(
                this, false, true,
                true, true);

        retrieveInformationFromLoadScreen();

        int N = cardIds.length;

        int nextNumber = minNumber;
        for (int i = 0; i < N; i++) {

            int id = cardIds[i];
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
            digitTxt.setText(String.valueOf(nextNumber));

             /*
            Set German
             */
            String germanWord = numberManager.getGermanFromDigit(
                    nextNumber);
            germanTxt.setText(germanWord);

            /*
            Set Audio Listener For Play Button
             */
            playBtn.setOnClickListener(new MediaListener(nextNumber));

            nextNumber++;
        }
    }

    private class MediaListener implements View.OnClickListener {

        private int mediaNumber;

        public MediaListener(int n) {
            super();
            this.mediaNumber = n;
        }

        @Override
        public void onClick(View v) {
            audioManager.play(mediaNumber);
        }
    }
}
