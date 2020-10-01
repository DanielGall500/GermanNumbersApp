package ucd.danielgall.klangapp.activity_managers;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.activities.scores.ScoreBoardScreen;

public class ScoresActivityManager extends NextActivityManager {

    private Class nextActivity = ScoreBoardScreen.class;

    public ScoresActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(nextActivity);
    }
}
