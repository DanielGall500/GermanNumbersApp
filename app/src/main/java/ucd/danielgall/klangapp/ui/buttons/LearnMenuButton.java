package ucd.danielgall.klangapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.activity_managers.LearnActivityManager;
import ucd.danielgall.klangapp.mechanics.game.Difficulty;

public class LearnMenuButton extends GeneralButton {

    LearnActivityManager learnActivityManager;
    private Difficulty currDiff;

    public LearnMenuButton(Context context, AppCompatActivity activity,
                           int resId) {
        super(context, activity, resId);
        learnActivityManager = new LearnActivityManager(context, activity);
    }

    public void setDifficulty(Difficulty diff) {
        this.currDiff = diff;
    }

    @Override
    protected void onAnimEnd() {
        learnActivityManager.run();
    }
}
