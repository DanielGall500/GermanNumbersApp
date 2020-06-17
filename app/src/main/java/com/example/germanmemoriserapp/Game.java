package com.example.germanmemoriserapp;

public class Game {

    enum GAME_STATE {
        NO_CHANGE, NEW_TURN, GAME_OVER
    };

    private SoundManager soundManager;
    private Score playerScore;
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
        playAudioClip();
    }

    private void onNewTurn() {
        newNumber();
        playAudioClip();
    }

    private void onGameOver() {
        soundManager.reset();
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
                onNewTurn();
                break;
            case GAME_OVER:
                onGameOver();
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
        return input.equals(String.valueOf(currentNumber));
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

    public boolean isEndOfGame() {
        return !soundManager.hasNext();
    }

}
