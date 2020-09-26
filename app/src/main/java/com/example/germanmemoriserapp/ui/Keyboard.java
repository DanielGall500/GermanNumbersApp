package com.example.germanmemoriserapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.germanmemoriserapp.mechanics.game.Game;

import java.util.HashMap;

public class Keyboard implements View.OnClickListener {

    final int wait = 1000; //ms

    private final String TXT_CLEAR = "";

    private final int NUM_DIGITS = 10;
    Handler keyboardHandler;
    Handler inputFieldHandler;
    Handler buttonHandler;
    HashMap<Integer, Integer> buttonIdRef;
    Input INPUT;
    Game GAME;
    BUTTON_STATE[] buttonStates;
    Handler mHandler = new Handler();
    int mDigit;

    private boolean isGameReady = false;

    public Keyboard(Handler inputFieldHandler, Handler buttonHandler,
                    Handler keyboardHandler, HashMap<Integer, Integer> idRef,
                    Game game) {

        this.keyboardHandler = keyboardHandler;
        this.buttonHandler = buttonHandler;
        this.inputFieldHandler = inputFieldHandler;
        this.buttonIdRef = idRef;
        this.GAME = game;

        INPUT = new Input();

        /*
        Initialise all button states to unpressed.
         */
        buttonStates = new BUTTON_STATE[NUM_DIGITS];

        for (BUTTON_STATE state : buttonStates)
            state = BUTTON_STATE.UNPRESSED;

    }

    public void updateCorrectNumber(int num) {
        INPUT.newTurn(num);
    }

    public KEYBOARD_STATE getNextState(int digit) {
        return INPUT.parseInput(digit);
    }

    @Override
    public void onClick(View v) {

        /*
        If the game isn't ready for UI input yet,
        don't allow the user to press anything.
         */
        if(!isGameReady)
            return;

        int id = v.getId();

        int digit = getDigitFromId(id);

        KEYBOARD_STATE nextState = INPUT.parseInput(digit);
        String userInput = INPUT.get();

        /*
        Update the keyboard button's state.
         */
        switch (nextState) {
            case VALID:
                onValidMove(digit);
                break;
            case INVALID:
                INPUT.clear();
                onInvalidMove(digit);
                break;
        }

        /*
        Check if our new input warrants a new turn / game over.
         */
        Message userInputMsg = new Message();
        userInputMsg.obj = userInput;
        keyboardHandler.sendMessage(userInputMsg);
    }

    public void onValidMove(int digit) {

        mDigit = digit;

        setValid(mDigit);
    }

    public void onInvalidMove(int digit) {

        setInvalid(digit);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clear();

                //Reset text entry box
                INPUT.clear();
                updateFieldText(getInput());
            }
        }, wait);

    }

    public void setGameReady(boolean isReady) {
        this.isGameReady = isReady;
    }

    public boolean isGameReady() {
        return this.isGameReady;
    }

    private void updateFieldText(String s) {
        sendMsgToHandler(inputFieldHandler, s);
    }

    private int getDigitFromId(int id) {
        if (buttonIdRef.containsKey(id))
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

    public void clearInput() {
        INPUT.clear();
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
        for (int d = 0; d < NUM_DIGITS; d++) {
            BUTTON_STATE state = buttonStates[d];

            boolean isClear = (state == BUTTON_STATE.UNPRESSED);

            if (!isClear)
                setUnpressed(d);
        }
    }

    public enum BUTTON_STATE {UNPRESSED, VALID, INVALID}

    public enum KEYBOARD_STATE {VALID, INVALID}

    class Input {

        private String current = "";
        private String correctNumber = "";

        public Input() {
        }


        public KEYBOARD_STATE parseInput(int digit) {

            add(digit);

            if (isNextDigit(digit)) {
                return KEYBOARD_STATE.VALID;
            } else {
                return KEYBOARD_STATE.INVALID;
            }
        }

        private void add(int n) {
            current += String.valueOf(n);
        }

        public void clear() {
            current = "";
        }

        private boolean isNextDigit(int digit) {
            int correct = this.getNextDigit();
            return (correct == digit);
        }

        private int getNextDigit() {
            boolean isNextDigit = size() <= correctNumSize();

            if (isNextDigit) {
                return Integer.parseInt(String.valueOf(
                        correctNumber.charAt(size() - 1)));
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
}
