package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;
import ucd.danielgall.klangapp.activities.load.LoadAudioScreen;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;

public class RetryButton extends GeneralButton {

    private int difficulty;
    private Context appContext;
    private AppCompatActivity appActivity;

    public RetryButton(Context context, AppCompatActivity activity,
                       int resId, int diffId) {
        super(context, activity, resId);
        this.appContext = context;
        this.appActivity = activity;
        this.difficulty = diffId;
    }

    @Override
    protected void onAnimEnd() {

        final String isGameKey = appActivity.getString(
                R.string.load_screen_isGameBoolean);

        final String infoKey = appActivity.getString(
                R.string.load_screen_information);

        NextActivityManager nextActivityManager = new NextActivityManager(
                appContext, appActivity);

        nextActivityManager.setNextActivity(LoadAudioScreen.class);
        nextActivityManager.addInformation(isGameKey, true);
        nextActivityManager.addInformation(infoKey, difficulty);
        nextActivityManager.run();

    }
}
