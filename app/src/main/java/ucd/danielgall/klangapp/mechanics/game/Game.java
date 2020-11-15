package ucd.danielgall.klangapp.mechanics.game;

import android.content.Context;
import ucd.danielgall.klangapp.mechanics.score.Score;
import ucd.danielgall.klangapp.sound.SoundSystem;
import ucd.danielgall.klangapp.sound.elements.UIClip;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.mechanics.game_elements.LifeManager;
import ucd.danielgall.klangapp.mechanics.game_elements.RelistenManager;

import java.util.ArrayList;

public class Game {

    /* Audio */
    public static final int NUMBER_CLIPS = 10;

    public static final int UI_CLIPS = 4;
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
    private Score playerScore;

    private SoundSystem soundSystem;


    /*testing*/
    private int nLives;
    private int nRelistens;
    private LifeManager gameLives;
    private RelistenManager gameRelistens;
    private ArrayList<Integer> loadedNumbers;
    private int loadedNumbersCurrentIndx = -1;
    private boolean gameIsLost = false;
    private Context appContext;

    public Game(Context appContext, AppCompatActivity appActivity, Difficulty.Level level, ArrayList<Integer> numberArr) {
        playerScore = new Score();

        this.appContext = appContext;
        this.loadedNumbers = numberArr;

        soundSystem = new SoundSystem(appContext);

        setupDifficultySettings(level);

        gameLives = new LifeManager(nLives);
        gameRelistens = new RelistenManager(nRelistens);
    }

    public void setupDifficultySettings(Difficulty.Level diff) {
        switch (diff) {
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

    public void relisten(TextView relistenTxtView) {

        if (!gameRelistens.isOutOfListens()) {

            //Decrement Re-listens & Update UI
            gameRelistens.decrementWithUIUpdate(relistenTxtView);

            //Play The Clip
            this.playAudioClip();

        } else {

            //TODO: Alert out of listens

        }
    }

    public boolean canRelisten() {
        return !gameRelistens.isOutOfListens();
    }

    public void begin() {
        setNewCorrectNumber();
        playAudioClip();
    }

    private void onNewTurn() {

        soundSystem.play(UIClip.GAME_CORRECT);

        newNumber();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                playAudioClip();
            }
        }, 2000);


    }

    private void onGameWon() {
        soundSystem.play(UIClip.GAME_WON);
    }

    private void onGameLost() {
        soundSystem.play(UIClip.GAME_LOST);
    }

    private void onFirstCorrect() {
        soundSystem.play(UIClip.GAME_CORRECT);
    }

    public void playAudioClip() {
        soundSystem.play(getCurrentNumber());
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
            case GAME_WON:
                onGameWon();
                break;
            case GAME_LOST:
                onGameLost();
                break;
            case FIRST_CORRECT:
                onFirstCorrect();
                break;
            case NO_CHANGE:
                break;
        }
    }

    public boolean beginsNumberCorrectly(String input) {
        String curr = String.valueOf(getCurrentNumber());
        char first = curr.charAt(0);

        return (first == input.charAt(0));
    }

    /*
    Get the next state of the game based on
    user input.
     */
    public GAME_STATE getState(String input, TextView lifeTxtView) {

        boolean isOneDigit = (input.length() == 1);

        if (inputIsCorrect(input)) {
            incrementScore();

            return isEndOfGame() ? GAME_STATE.GAME_WON : GAME_STATE.NEW_TURN;

        } else if (beginsNumberCorrectly(input) && isOneDigit) {
            return GAME_STATE.FIRST_CORRECT;
        } else {

            //Take Away A Life
            if (gameLives.hasLives()) {
                gameLives.decrementWithUIUpdate(lifeTxtView);
            }


            soundSystem.play(UIClip.GAME_INCORRECT);

            //Check If Out Of Lives
            if (gameLives.isOutOfLives()) {

                /*
                We've lost the game!
                 */
                gameIsLost = true;

                return GAME_STATE.GAME_LOST;
            } else {

                /*
                We haven't lost yet..
                 */
                return GAME_STATE.NO_CHANGE;
            }
        }
    }

    private boolean inputIsCorrect(String input) {
        return input.equals(String.valueOf(getCurrentNumber()));
    }

    private void newNumber() {
        if (!isEndOfGame()) {
            setNewCorrectNumber();
        } else {
            throw new RuntimeException("No Numbers Left");
        }
    }

    public int getNumber() {
        return getCurrentNumber();
    }

    public int getScore() {
        return playerScore.getScore();
    }

    private void incrementScore() {
        playerScore.increment();
    }

    public boolean isEndOfGame() {
        return (loadedNumbersCurrentIndx == NUMBER_CLIPS - 1);
    }

    private void setNewCorrectNumber() {
        loadedNumbersCurrentIndx++;
    }

    private int getCurrentNumber() {
        return loadedNumbers.get(loadedNumbersCurrentIndx);
    }

    public enum GAME_STATE {
        NO_CHANGE, NEW_TURN, FIRST_CORRECT, GAME_WON, GAME_LOST
    }

}
