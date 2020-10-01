package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.activity_managers.NextActivityManager;

public class MenuButton extends GeneralButton {

    private NextActivityManager nextActivityManager;

    public MenuButton(Context context, AppCompatActivity activity, int resId,
                      NextActivityManager nextActivityManager) {
        super(context, activity, resId);
        this.nextActivityManager = nextActivityManager;
    }

    @Override
    protected void onAnimEnd() {
        nextActivityManager.run();
    }

}
