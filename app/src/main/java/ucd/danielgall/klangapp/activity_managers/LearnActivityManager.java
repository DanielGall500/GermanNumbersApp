package ucd.danielgall.klangapp.activity_managers;

import android.content.Context;
import ucd.danielgall.klangapp.activities.learn.LearnScreen;

import androidx.appcompat.app.AppCompatActivity;

public class LearnActivityManager extends NextActivityManager {
    private Class learnScreen = LearnScreen.class;

    public LearnActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(learnScreen);
    }
}
