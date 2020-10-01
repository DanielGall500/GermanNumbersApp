package ucd.danielgall.klangapp.activity_managers;

import android.content.Context;
import ucd.danielgall.klangapp.activities.menu.MenuScreen;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivityManager extends NextActivityManager {
    private Class menuScreen = MenuScreen.class;

    public MenuActivityManager(Context context, AppCompatActivity activity) {
        super(context, activity);
        setNextActivity(menuScreen);
    }
}
