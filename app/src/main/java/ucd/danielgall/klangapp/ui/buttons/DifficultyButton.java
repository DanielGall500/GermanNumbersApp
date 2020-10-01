package ucd.danielgall.klangapp.ui.buttons;

import ucd.danielgall.klangapp.listeners.DifficultyListener;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.mechanics.game.Difficulty;

public class DifficultyButton {

    private ImageButton diffBtn;

    private int btnResourceId;
    private int resourcePressedImgId;
    private int resourceUnpressedImgId;

    private boolean isPressed;

    private Difficulty currDifficulty;

    public DifficultyButton(AppCompatActivity activity, int btnId,
                            int resUnpressed, int resPressed,
                            int difficultyId, boolean isPressed) {

        diffBtn = activity.findViewById(btnId);
        currDifficulty = new Difficulty(difficultyId);

        /* Resources */
        resourcePressedImgId = resPressed;
        resourceUnpressedImgId = resUnpressed;
        btnResourceId = btnId;

        /* Initial State */
        setState(isPressed);
    }

    public void setListener(DifficultyListener l) {
        diffBtn.setOnClickListener(l);
    }

    public void flipState() {
        setState(!isPressed);
    }

    public int getId() {
        return btnResourceId;
    }

    private void pressBtnUI() {
        diffBtn.setImageResource(resourcePressedImgId);
    }

    private void unpressBtnUI() {
        diffBtn.setImageResource(resourceUnpressedImgId);
    }

    private void setState(boolean isPressed) {

        //Update External State
        if (isPressed) {
            pressBtnUI();
        } else {
            unpressBtnUI();
        }

        //Update Internal State
        this.isPressed = isPressed;
    }
}
