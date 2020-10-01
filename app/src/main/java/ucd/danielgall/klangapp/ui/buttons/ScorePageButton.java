package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.activities.load.LoadScoresScreen;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;
import ucd.danielgall.klangapp.activity_managers.ScoresActivityManager;

public class ScorePageButton extends GeneralButton {

    private Class loadScreen = LoadScoresScreen.class;

    private NextActivityManager scoresActivityManager;

    private String loadScoreKey;
    private Context appContext;
    private AppCompatActivity appActivity;

    private int loadId;

    public ScorePageButton(Context context, AppCompatActivity activity,
                           int resId, int loadId) {
        super(context, activity, resId);

        this.appContext = context;
        this.appActivity = activity;
        this.loadId = loadId;

        scoresActivityManager = new ScoresActivityManager(
                getContext(), getActivity());
        scoresActivityManager.setNextActivity(loadScreen);

        this.loadScoreKey = activity.getString(
                R.string.score_load_intent_key);
        scoresActivityManager.addInformation(loadScoreKey, loadId);
    }

    @Override
    public void onAnimEnd() {
        scoresActivityManager.run();
    }
}
