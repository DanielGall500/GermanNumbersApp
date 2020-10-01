package ucd.danielgall.klangapp.ui.buttons;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.ui.Keyboard;

public class ButtonResource {

    /*
    Game Buttons
     */
    int[] unpressedBtnImgs = new int[]{
            R.drawable.game_zero_btn_unpressed, R.drawable.game_one_btn_unpressed,
            R.drawable.game_two_btn_unpressed, R.drawable.game_three_btn_unpressed,
            R.drawable.game_four_btn_unpressed, R.drawable.game_five_btn_unpressed,
            R.drawable.game_six_btn_unpressed, R.drawable.game_seven_btn_unpressed,
            R.drawable.game_eight_btn_unpressed, R.drawable.game_nine_btn_unpressed
    };

    int[] correctBtnImgs = new int[]{
            R.drawable.keyb_0_correct, R.drawable.keyb_1_correct, R.drawable.keyb_2_correct,
            R.drawable.keyb_3_correct, R.drawable.keyb_4_correct, R.drawable.keyb_5_correct,
            R.drawable.keyb_6_correct, R.drawable.keyb_7_correct, R.drawable.keyb_8_correct,
            R.drawable.keyb_9_correct
    };

    int[] incorrectBtnImgs = new int[]{
            R.drawable.keyb_0_incorrect, R.drawable.keyb_1_incorrect, R.drawable.keyb_2_incorrect,
            R.drawable.keyb_3_incorrect, R.drawable.keyb_4_incorrect, R.drawable.keyb_5_incorrect,
            R.drawable.keyb_6_incorrect, R.drawable.keyb_7_incorrect, R.drawable.keyb_8_incorrect,
            R.drawable.keyb_9_incorrect
    };

    /*
    Menu Buttons
     */

    public ButtonResource() {
    }

    public int getGameBtn(int digit, Keyboard.BUTTON_STATE state) {
        switch (state) {
            case VALID:
                return correctBtnImgs[digit];
            case INVALID:
                return incorrectBtnImgs[digit];
            default: //unpressed
                return unpressedBtnImgs[digit];
        }
    }
}
