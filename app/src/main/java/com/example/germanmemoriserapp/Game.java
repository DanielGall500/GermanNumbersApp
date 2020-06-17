package com.example.germanmemoriserapp;

public class Game {

    public enum GAME_STATE {
        NO_CHANGE, NEW_TURN, GAME_OVER
    };

    private Score playerScore;
    private SoundManager soundManager;

    private int currentNumber;

    public Game() {
        playerScore = new Score();
        soundManager = SoundManager.get();
    }

    /*
    Actions
     */
    public void begin() {
        newNumber();
    }

    private void newTurn() {
        newNumber();
        playAudioClip();
    }

    private void gameOver() {
        soundManager.release();
    }

    private void playAudioClip() {
        soundManager.play(currentNumber);
    }

    /*
    Execute an action based on the next
    game state.
     */
    public void execute(GAME_STATE state) {
        switch (state) {
            case NEW_TURN:
                newTurn();
                break;
            case GAME_OVER:
                gameOver();
                break;
            case NO_CHANGE:
                break;
        }
    }

    /*
    Get the next state of the game based on
    user input.
     */
    public GAME_STATE getState(String input) {

        boolean gameOver = !soundManager.hasNext();

        if(inputIsCorrect(input))
            incrementScore();
        else
            return GAME_STATE.NO_CHANGE;

        return gameOver ? GAME_STATE.GAME_OVER : GAME_STATE.NEW_TURN;
    }

    private boolean inputIsCorrect(String input) {
        if(!isValidInput(input))
            return false;

        return input == String.valueOf(currentNumber);
    }

    private void newNumber() {
        if(soundManager.hasNext())
            currentNumber = soundManager.next();
        else
            throw new RuntimeException("No Numbers Left");
    }

    public int getNumber() {
        return currentNumber;
    }

    public int getScore() {
        return playerScore.getScore();
    }

    private void incrementScore() {
        playerScore.increment();
    }

    //TODO: ew
    public boolean isValidInput(String input) {
        if (input.length() == 0)
            return false;

        char[] chars = input.toCharArray();
        int digit;

        for (char c : chars) {
            digit = ((int) c - 48);

            if (digit < 0 || digit > 9)
                return false;
        }
        return true;
    }

    public boolean isEndOfGame() {
        return !soundManager.hasNext();
    }

}
