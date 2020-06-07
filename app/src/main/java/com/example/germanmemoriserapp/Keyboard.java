package com.example.germanmemoriserapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Keyboard {

    ImageButton[] digitButtons;

    public Keyboard(ImageButton[] digits)
    {
        this.digitButtons = digits;

        ImageButton zero = getDigitBtn(0);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
            }
        });
    }

    private ImageButton getDigitBtn(int digit)
    {
        if(digit < 0 || digit > 9)
            throw new IllegalArgumentException("Invalid Digit");

        return digitButtons[digit];
    }

}
