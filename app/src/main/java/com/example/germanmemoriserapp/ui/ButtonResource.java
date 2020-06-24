package com.example.germanmemoriserapp.ui;

import com.example.germanmemoriserapp.R;

public class ButtonResource {

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

    public ButtonResource() {}

    public int get(int digit, Keyboard.BUTTON_STATE state) {
        switch(state) {
            case VALID:
                return correctBtnImgs[digit];
            case INVALID:
                return incorrectBtnImgs[digit];
            default: //unpressed
                return unpressedBtnImgs[digit];
        }
    }
}
