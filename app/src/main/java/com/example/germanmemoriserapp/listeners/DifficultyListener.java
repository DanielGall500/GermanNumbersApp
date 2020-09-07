package com.example.germanmemoriserapp.listeners;

import android.view.View;

import com.example.germanmemoriserapp.mechanics.Difficulty;
import com.example.germanmemoriserapp.ui.DifficultyButton;

public class DifficultyListener implements View.OnClickListener {

    Difficulty currDifficulty;

    private DifficultyButton beginnerBtn;
    private DifficultyButton normalBtn;
    private DifficultyButton masterBtn;

    public DifficultyListener(DifficultyButton beginnerBtn,
                              DifficultyButton normalBtn, DifficultyButton masterBtn,
                              int initialDiff) {

        currDifficulty = new Difficulty(initialDiff);

        this.beginnerBtn = beginnerBtn;
        this.normalBtn = normalBtn;
        this.masterBtn = masterBtn;
    }

    public int getId() {
        return currDifficulty.getId();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        Difficulty.Level btnDiffLevel = getButtonLevel(id);

        /*
        If we clicked the button that our difficulty is
        still set to, do nothing.
         */
        if(btnDiffLevel == currDifficulty.getLevel()) {
            return;
        }

        //Flip Old Button
        flipBtn(btnDiffLevel);

        //Flip Current Button
        flipBtn(currDifficulty.getLevel());

        //Update Current Difficulty State
        currDifficulty = new Difficulty(btnDiffLevel);
    }

    public Difficulty.Level getButtonLevel(int resId) {
        if(resId == beginnerBtn.getId()) {
            return Difficulty.Level.BEGINNER;
        }
        else if(resId == normalBtn.getId()) {
            return Difficulty.Level.NORMAL;
        }
        else if(resId == masterBtn.getId()) {
            return Difficulty.Level.MASTER;
        }
        else {
            throw new IllegalArgumentException("Invalid Res Id");
        }
    }

    public void flipBtn(Difficulty.Level d) {
        switch(d) {
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
