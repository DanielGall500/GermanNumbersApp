package ucd.danielgall.klangapp.listeners;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.mechanics.game.Difficulty;
import ucd.danielgall.klangapp.sound.SoundManager;
import ucd.danielgall.klangapp.sound.elements.UIClip;
import ucd.danielgall.klangapp.ui.buttons.DifficultyButton;

public class DifficultyListener implements View.OnClickListener {

    Difficulty currDifficulty;

    private DifficultyButton beginnerBtn;
    private DifficultyButton normalBtn;
    private DifficultyButton masterBtn;

    private Handler updateDifficultyHandler;

    private Context appContext;
    private AppCompatActivity appActivity;

    private SoundManager soundManager;

    public DifficultyListener(Context context,
                              AppCompatActivity activity,
                              DifficultyButton beginnerBtn,
                              DifficultyButton normalBtn, DifficultyButton masterBtn,
                              Handler updateDifficulty,
                              int initialDiff) {

        currDifficulty = new Difficulty(initialDiff);

        this.beginnerBtn = beginnerBtn;
        this.normalBtn = normalBtn;
        this.masterBtn = masterBtn;

        this.appContext = context;
        this.appActivity = activity;

        this.soundManager = SoundManager.get(appContext, appActivity);

        this.updateDifficultyHandler = updateDifficulty;
    }

    public int getId() {
        return currDifficulty.getId();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        Difficulty.Level btnDiffLevel = getButtonLevel(id);

        soundManager.play(UIClip.GENERAL_BUTTON_CLICK);

        /*
        If we clicked the button that our difficulty is
        still set to, do nothing.
         */
        if (btnDiffLevel == currDifficulty.getLevel()) {
            return;
        }

        //Flip Old Button
        flipBtn(btnDiffLevel);

        //Flip Current Button
        flipBtn(currDifficulty.getLevel());

        //Update Current Difficulty State
        currDifficulty = new Difficulty(btnDiffLevel);

        //Update the menu's difficulty
        Message newDiff = new Message();
        newDiff.arg1 = currDifficulty.getId();
        updateDifficultyHandler.sendMessage(newDiff);
    }

    public Difficulty.Level getButtonLevel(int resId) {
        if (resId == beginnerBtn.getId()) {
            return Difficulty.Level.BEGINNER;
        } else if (resId == normalBtn.getId()) {
            return Difficulty.Level.NORMAL;
        } else if (resId == masterBtn.getId()) {
            return Difficulty.Level.MASTER;
        } else {
            throw new IllegalArgumentException("Invalid Res Id");
        }
    }

    public void flipBtn(Difficulty.Level d) {
        switch (d) {
            case BEGINNER:
                beginnerBtn.flipState();
                break;
            case NORMAL:
                normalBtn.flipState();
                break;
            case MASTER:
                masterBtn.flipState();
                break;
        }
    }

}
