package com.example.germanmemoriserapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class Keyboard implements View.OnClickListener {

    final int NUM_DIGITS = 10;
    ImageButton[] digitButtons;
    String input = "";

    EditText inputField;

    HashMap<Integer, Integer> associatedDigits;

    public Keyboard(ImageButton[] digits, EditText inputArea)
    {
        this.digitButtons = digits;
        this.inputField = inputArea;

        ImageButton zero = getDigitBtn(0);

        //Map Button ID -> Corresponding Digit
        associatedDigits = new HashMap<>();

        for(int i = 0; i < NUM_DIGITS; i++)
            associatedDigits.put(digitButtons[i].getId(), i);

        for(ImageButton b : digitButtons)
            b.setOnClickListener(this);
    }

    private ImageButton getDigitBtn(int digit)
    {
        if(digit < 0 || digit > 9)
            throw new IllegalArgumentException("Invalid Digit");

        return digitButtons[digit];
    }

    private void addInput(int digit)
    {
        input += String.valueOf(digit);
    }

    public String getInput()
    {
        return this.input;
    }

    public void clearInput()
    {
        this.input = "";
    }

    @Override
    public void onClick(View v) {

        int ID = v.getId();

        int digit = associatedDigits.get(ID);

        addInput(digit);

        inputField.setText(getInput());
    }
}
