package com.example.germanmemoriserapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.germanmemoriserapp.mechanics.Game;

import java.util.HashMap;

public class Keyboard implements View.OnClickListener {

    public enum BUTTON_STATE {UNPRESSED, VALID, INVALID};
    public enum KEYBOARD_STATE {VALID, INVALID};

    private final String TXT_CLEAR = "";

    private final int NUM_DIGITS = 10;

    Handler keyboardHandler;
    Handler inputFieldHandler;
    Handler buttonHandler;
    HashMap<Integer, Integer> buttonIdRef;
    Input INPUT;
    Game GAME;

    BUTTON_STATE[] buttonStates;

    class Input {

        private String current = "";
        private String correctNumber = "";

        public Input() {}


        public KEYBOARD_STATE inputRequest(int digit) {
            if(isNextDigit(digit)) {
                add(digit);
                System.out.println("Requesting Valid");
                return KEYBOARD_STATE.VALID;
            }
            else {
                clear();
                System.out.println("Requesting Invalid");
                return KEYBOARD_STATE.INVALID;
            }
        }

        private void add(int n) {
            current += String.valueOf(n);
        }

        private void clear() {
            current = "";
        }

        private boolean isNextDigit(int digit) {
            int correct = this.getNextDigit();
            return (correct == digit);
        }

        private int getNextDigit() {
            boolean isNextDigit = size() < correctNumSize();

            if(isNextDigit) {
                return Integer.parseInt(String.valueOf(
                        correctNumber.charAt(size())));
            } else {
                throw new IllegalArgumentException("No More Digits");
            }
        }
        /*
        public void setCorrectNumber(String n) {
            this.correctNumber = n;
        }*/

        private int size() {
            return current.length();
        }

        private int correctNumSize() {
            return correctNumber.length();
        }

        public void newTurn(int num) {
            this.correctNumber = String.valueOf(num);
            clear();
        }

        public String get() {
            return this.current;
        }
    }

    public void updateCorrectNumber(int num) {
        INPUT.newTurn(num);
    }

    public Keyboard(Handler inputFieldHandler, Handler buttonHandler,
                    HashMap<Integer, Integer> idRef,
                    Handler handler, Game game) {

        this.keyboardHandler = handler;
        this.buttonHandler = buttonHandler;
        this.inputFieldHandler = inputFieldHandler;
        this.buttonIdRef = idRef;
        this.GAME = game;

        INPUT = new Input();

        /*
        Initialise all button states to unpressed.
         */
        buttonStates = new BUTTON_STATE[NUM_DIGITS];

        for(BUTTON_STATE state : buttonStates)
            state = BUTTON_STATE.UNPRESSED;

    }

    Handler mHandler = new Handler();

    @Override
    public void onClick(View v) {

        int id = v.getId();

        int digit = getDigitFromId(id);

        System.out.println("Clicked: " + digit);

        KEYBOARD_STATE nextState = INPUT.inputRequest(digit);

        System.out.println(nextState.toString());

        switch(nextState) {
            case VALID:
                onValidMove(digit);
            case INVALID:
                onInvalidMove(digit);
        }

        //Tell the world that there's new input
        keyboardHandler.sendEmptyMessage(0);
    }

    int mDigit;
    private void onValidMove(int digit) {

        System.out.println("onValidMove");

        final int wait = 1000; //ms

        mDigit = digit;

        sendMsgToHandler(inputFieldHandler, INPUT.get());
        setValid(mDigit);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clear();
            }
        }, wait);
    }

    private void onInvalidMove(int digit) {

        System.out.println("onInvalidMove");

        setInvalid(digit);
        requestInputFieldSetText(TXT_CLEAR);

    }

    private void onNewTurn() {

    }

    private int getDigitFromId(int id) {
        if(buttonIdRef.containsKey(id))
            return buttonIdRef.get(id);
        else
            throw new IllegalArgumentException("Invalid ID");
    }

    private void setValid(int digit) {
        requestButtonStateUpdate(BUTTON_STATE.VALID, digit);
    }

    private void setInvalid(int digit) {
        requestButtonStateUpdate(BUTTON_STATE.INVALID, digit);
    }

    private void setUnpressed(int digit) {
        requestButtonStateUpdate(BUTTON_STATE.UNPRESSED, digit);
    }

    private void sendMsgToHandler(Handler h, String s) {
        Message msg = new Message();
        msg.obj = s;
        msg.setTarget(h);
        msg.sendToTarget();
    }

    private void sendMsgToHandler(Handler h, Message msg) {
        msg.setTarget(h);
        msg.sendToTarget();
    }

    private void requestInputFieldSetText(String txt) {
        sendMsgToHandler(inputFieldHandler, txt);
    }

    private void requestButtonStateUpdate(BUTTON_STATE state, int digit) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("STATE", state);
        bundle.putSerializable("DIGIT", digit);

        Message msg = new Message();
        msg.setData(bundle);
        msg.setTarget(buttonHandler);
        msg.sendToTarget();
    }

    public String getInput() {
        return INPUT.get();
    }

    /* Reset every button on the keyboard to unpressed */
    public void clear() {
        for(int d = 0; d < NUM_DIGITS; d++) {
            BUTTON_STATE state = buttonStates[d];

            boolean isClear = (state == BUTTON_STATE.UNPRESSED);

            if(!isClear)
                setUnpressed(d);
        }
    }
}
