package com.example.germanmemoriserapp.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.mechanics.Difficulty;
import com.example.germanmemoriserapp.ui.ButtonResource;

public class DifficultyListener implements View.OnClickListener {

    private final Difficulty.Level INITIAL_DIFFICULTY = Difficulty.Level.NORMAL;

    private Difficulty.Level current = INITIAL_DIFFICULTY;
    private int currentId = R.id.diffNormalBtn;

    private Activity activity;
    private ButtonResource btnRes;

    Difficulty currDifficulty;

    public DifficultyListener(AppCompatActivity activity, int initialDiff) {
        this.activity = activity;
        this.btnRes = new ButtonResource();

        currDifficulty = new Difficulty(initialDiff);

        //Set our initial button state
        ImageButton initialBtn = activity.findViewById(currentId);
        setButtonState(initialBtn, current, true);
    }

    public int getId() {
        return currDifficulty.getId();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //Old and new difficulty buttons
        Difficulty.Level oldDifficulty = current;
        ImageButton oldBtn = activity.findViewById(currentId);
        ImageButton newBtn = activity.findViewById(id);

        //Set the new id
        currentId = id;

        switch(id) {
            case R.id.diffBeginnerBtn:
                currDifficulty.setDifficulty(Difficulty.Level.BEGINNER);
                break;
            case R.id.diffNormalBtn:
                currDifficulty.setDifficulty(Difficulty.Level.NORMAL);
                break;
            case R.id.diffMasterBtn:
                currDifficulty.setDifficulty(Difficulty.Level.MASTER);
                break;
        }

        //Unpress our old difficulty
        setButtonState(oldBtn, oldDifficulty, false);

        //Press our new difficulty
        setButtonState(newBtn, current, true);
    }

    public void setButtonState(ImageButton b, Difficulty.Level diff, boolean pressed) {
        int res = btnRes.getMenuBtn(diff, pressed);
        b.setImageResource(res);
    }
}
