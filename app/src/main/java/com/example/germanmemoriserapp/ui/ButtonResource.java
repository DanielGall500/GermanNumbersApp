package com.example.germanmemoriserapp.ui;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.enums.Difficulty;

public class ButtonResource {

    /*
    Game Buttons
     */
    int[] unpressedBtnImgs = new int[] {
            R.drawable.keyb_0, R.drawable.keyb_1, R.drawable.keyb_2,
            R.drawable.keyb_3, R.drawable.keyb_4, R.drawable.keyb_5,
            R.drawable.keyb_6, R.drawable.keyb_7, R.drawable.keyb_8,
            R.drawable.keyb_9
    };

    int[] correctBtnImgs = new int[] {
            R.drawable.keyb_0_correct, R.drawable.keyb_1_correct, R.drawable.keyb_2_correct,
            R.drawable.keyb_3_correct, R.drawable.keyb_4_correct, R.drawable.keyb_5_correct,
            R.drawable.keyb_6_correct, R.drawable.keyb_7_correct, R.drawable.keyb_8_correct,
            R.drawable.keyb_9_correct
    };

    int[] incorrectBtnImgs = new int[] {
            R.drawable.keyb_0_incorrect, R.drawable.keyb_1_incorrect, R.drawable.keyb_2_incorrect,
            R.drawable.keyb_3_incorrect, R.drawable.keyb_4_incorrect, R.drawable.keyb_5_incorrect,
            R.drawable.keyb_6_incorrect, R.drawable.keyb_7_incorrect, R.drawable.keyb_8_incorrect,
            R.drawable.keyb_9_incorrect
    };

    /*
    Menu Buttons
     */
    private final int[] beginnerRes = new int[] {
            R.drawable.beginner_btn,
            R.drawable.beginner_btn_pressed
    };

    private final int[] normalRes = new int[] {
            R.drawable.normal_btn,
            R.drawable.normal_btn_pressed
    };

    private final int[] masterRes = new int[] {
            R.drawable.master_btn,
            R.drawable.master_btn_pressed
    };

    public ButtonResource() {}

    public int getGameBtn(int digit, Keyboard.BUTTON_STATE state) {
        switch(state) {
            case VALID:
                return correctBtnImgs[digit];
            case INVALID:
                return incorrectBtnImgs[digit];
            default: //unpressed
                return unpressedBtnImgs[digit];
        }
    }

    public int getMenuBtn(Difficulty d, boolean pressed) {

        int pressedPos = pressed ? 1 : 0;

        switch(d) {
            case BEGINNER:
                return beginnerRes[pressedPos];
            case NORMAL:
                return normalRes[pressedPos];
            default:
                return masterRes[pressedPos];
        }
    }


}
