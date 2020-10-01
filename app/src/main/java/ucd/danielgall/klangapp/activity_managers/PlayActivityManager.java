package ucd.danielgall.klangapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;

import ucd.danielgall.klangapp.activities.load.LoadAudioScreen;

public class PlayActivityManager extends NextActivityManager {

    private Class loadScreen = LoadAudioScreen.class;

    private String isGameKey;
    private String loadInfo;

    private int difficultyId;

    public PlayActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(loadScreen);

        isGameKey = getContext().getString(
                R.string.load_screen_isGameBoolean);

        loadInfo = getContext().getString(
                R.string.load_screen_information);
    }

    public void setDifficulty(int diffId) {
        this.difficultyId = diffId;
    }

    @Override
    public void run() {
        addInformation(isGameKey, true);
        addInformation(loadInfo, difficultyId);

        super.run();
    }


}
