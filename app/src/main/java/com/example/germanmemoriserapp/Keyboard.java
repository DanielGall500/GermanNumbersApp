package com.example.germanmemoriserapp;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class Keyboard implements View.OnClickListener {

    final int backBtnIndex = 10;
    final int NUM_DIGITS = 10;

    int numButtons;
    int backBtnID;

    HashMap<Integer, Integer> associatedDigits;
    ImageButton[] digitButtons;
    Handler keyboardHandler;
    EditText inputField;

    String input = "";
    int inputSize = 0;

    public Keyboard(ImageButton[] digits, int size, EditText inputArea, Handler handler) {
        this.inputField = inputArea;
        this.keyboardHandler = handler;
        this.numButtons = size;
        this.digitButtons = digits;
        this.backBtnID = digits[backBtnIndex].getId();

        //Map Button ID -> Corresponding Digit
        this.associatedDigits = new HashMap<>();

        for (int i = 0; i < NUM_DIGITS; i++)
            this.associatedDigits.put(digits[i].getId(), i);

        for (ImageButton b : digits)
            b.setOnClickListener(this);
    }

    private ImageButton getDigit(int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Invalid Digit");

        return digitButtons[digit];
    }

    private ImageButton getBack() {
        return digitButtons[backBtnIndex];
    }

    private void addInput(int digit) {
        input += String.valueOf(digit);
        inputSize++;
    }

    public String getInput() {
        return this.input;
    }

    private void setInput(String inp) {
        this.input = inp;
        this.inputSize = input.length();
    }

    public void clearInput() {
        this.input = "";
    }

    public int getInputSize() {
        return this.inputSize;
    }

    private void actionBack() {
        int N = getInputSize();

        if (N > 0) {
            String backBtnApplied = input.substring(0, N - 1);
            setInput(backBtnApplied);
        }
    }

    private boolean isBackButton(int ID) {
        return ID == backBtnID;
    }

    @Override
    public void onClick(View v) {

        int ID = v.getId();

        if (isBackButton(ID)) {
            actionBack();
        } else {
            int digit = associatedDigits.get(ID);
            addInput(digit);
        }

        inputField.setText(getInput());

        //Tell the world that there's new input
        keyboardHandler.sendEmptyMessage(0);
    }
}
