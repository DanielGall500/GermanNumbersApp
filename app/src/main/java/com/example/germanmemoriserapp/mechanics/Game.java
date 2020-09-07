package com.example.germanmemoriserapp.mechanics;

import android.widget.TextView;

import com.example.germanmemoriserapp.audio.SoundManager;

public class Game {

    public enum GAME_STATE {
        NO_CHANGE, NEW_TURN, GAME_OVER
    };

    public static final int MIN_NUM = 0;
    public static final int MAX_NUM = 99;

    public static final int BEGINNER_GAME_LIVES = 15;
    public static final int BEGINNER_LISTENS = 15;

    public static final int NORMAL_GAME_LIVES = 10;
    public static final int NORMAL_LISTENS = 10;

    public static final int MASTER_GAME_LIVES = 1;
    public static final int MASTER_LISTENS = 3;

    public static int GAME_LOST_VALUE = -1;
    public static String GAME_LOST_TEXT = "Out Of Lives!";

    private SoundManager soundManager;
    private Score playerScore;
    private int currentNumber;

    /*testing*/
    private int nLives;
    private int nRelistens;
    private LifeManager gameLives;
    private RelistenManager gameRelistens;

    private boolean gameIsLost = false;

    public Game(Difficulty.Level level) {
        playerScore = new Score();
        soundManager = SoundManager.get();

        setupDifficultySettings(level);

        gameLives = new LifeManager(nLives);
        gameRelistens = new RelistenManager(nRelistens);
    }

    public void setupDifficultySettings(Difficulty.Level diff) {
        switch(diff) {
            case BEGINNER:
                this.nLives = BEGINNER_GAME_LIVES;
                this.nRelistens = BEGINNER_LISTENS;
                break;
            case NORMAL:
                this.nLives = NORMAL_GAME_LIVES;
                this.nRelistens = NORMAL_LISTENS;
                break;
            case MASTER:
                this.nLives = MASTER_GAME_LIVES;
                this.nRelistens = MASTER_LISTENS;
                break;
        }
    }

    public int getLives() {
        return nLives;
    }

    public int getRelistens() {
        return nRelistens;
    }

    public boolean gameLost() {
        return gameIsLost;
    }

    /*
    Actions
     */

    public void relisten(TextView relistenTxtView) {

        if(!gameRelistens.isOutOfListens()) {

            //Decrement Re-listens & Update UI
            gameRelistens.decrementWithUIUpdate(relistenTxtView);

            //Play The Clip
            this.playAudioClip();

        } else {

            //TODO: Alert out of listens

        }
    }

    public int getStartingRelistens() {
        return nRelistens;
    }

    public boolean canRelisten() {
        return !gameRelistens.isOutOfListens();
    }

    public void begin() {
        newNumber();
        playAudioClip();
    }

    private void onNewTurn() {
        newNumber();
        playAudioClip();
    }

    private void onGameOver() {
        soundManager = null;
    }

    public void playAudioClip() {
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
    public GAME_STATE getState(String input, TextView lifeTxtView) {

        boolean gameOver = !soundManager.hasNext();

        if(inputIsCorrect(input))
            incrementScore();
        else {

            //Take Away A Life
            if(gameLives.hasLives()) {
                gameLives.decrementWithUIUpdate(lifeTxtView);
            }

            //Check If Out Of Lives
            if(gameLives.isOutOfLives()) {

                /*
                We've lost the game!
                 */
                gameIsLost = true;

                return GAME_STATE.GAME_OVER;
            } else {

                /*
                We haven't lost yet..
                 */
                return GAME_STATE.NO_CHANGE;
            }
        }

        return gameOver ? GAME_STATE.GAME_OVER : GAME_STATE.NEW_TURN;
    }

    private boolean inputIsCorrect(String input) {
        return input.equals(String.valueOf(currentNumber));
    }

    private void newNumber() {
        System.out.println("new number: ");
        if(soundManager.hasNext()) {
            setNewCorrectNumber();
        }
        else {
            throw new RuntimeException("No Numbers Left");
        }
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

    private void setNewCorrectNumber() {
        currentNumber = soundManager.next();
    }

}
