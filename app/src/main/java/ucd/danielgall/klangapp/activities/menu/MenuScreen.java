package ucd.danielgall.klangapp.activities.menu;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.listeners.DifficultyListener;
import ucd.danielgall.klangapp.mechanics.game.Difficulty;
import ucd.danielgall.klangapp.ui.buttons.DifficultyButton;
import ucd.danielgall.klangapp.ui.buttons.LearnMenuButton;
import ucd.danielgall.klangapp.ui.buttons.PlayMenuButton;
import ucd.danielgall.klangapp.ui.buttons.ScoresMenuButton;

public class MenuScreen extends AppCompatActivity {

    private final boolean INITIAL_BEGINNER_IS_PRESSED = false;
    private final boolean INITIAL_NORMAL_IS_PRESSED = true;
    private final boolean INITIAL_MASTER_IS_PRESSED = false;

    private final Difficulty INITIAL_DIFFICULTY =
            new Difficulty(Difficulty.Level.NORMAL);

    private final int beginnerId = Difficulty.getId(Difficulty.Level.BEGINNER);
    private final int normalId = Difficulty.getId(Difficulty.Level.NORMAL);
    private final int masterId = Difficulty.getId(Difficulty.Level.MASTER);
    DifficultyListener difficultyListener;

    private int[] difficultyBtnIds = new int[]{
            R.id.diffBeginnerBtn,
            R.id.diffNormalBtn,
            R.id.diffMasterBtn
    };
    private int[] unpressedImgIds = new int[]{
            R.drawable.menu_beginner_btn_unpressed,
            R.drawable.menu_normal_btn_unpressed,
            R.drawable.menu_master_btn_unpressed
    };
    private int[] pressedImgIds = new int[]{
            R.drawable.menu_beginner_btn_pressed,
            R.drawable.menu_normal_btn_pressed,
            R.drawable.menu_master_btn_pressed
    };
    private DifficultyButton beginnerBtn, normalBtn, masterBtn;
    private PlayMenuButton playMenuButton;
    private LearnMenuButton learnMenuButton;
    private ScoresMenuButton scoreMenuButton;

    @Override
    public void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu_screen);

        /* Primary Menu Buttons */
        playMenuButton = new PlayMenuButton(this, this, R.id.menuPlayBtn, INITIAL_DIFFICULTY.getId());
        learnMenuButton = new LearnMenuButton(this, this, R.id.menuLearnBtn);
        scoreMenuButton = new ScoresMenuButton(this, this, R.id.menuScoresBtn);

        /* Difficulty Menu Buttons */
        beginnerBtn = new DifficultyButton(
                this, difficultyBtnIds[beginnerId],
                unpressedImgIds[beginnerId], pressedImgIds[beginnerId],
                beginnerId, INITIAL_BEGINNER_IS_PRESSED);

        normalBtn = new DifficultyButton(
                this, difficultyBtnIds[normalId],
                unpressedImgIds[normalId], pressedImgIds[normalId],
                normalId, INITIAL_NORMAL_IS_PRESSED);

        masterBtn = new DifficultyButton(
                this, difficultyBtnIds[masterId],
                unpressedImgIds[masterId], pressedImgIds[masterId],
                masterId, INITIAL_MASTER_IS_PRESSED);

        difficultyListener = new DifficultyListener(this, this, beginnerBtn, normalBtn,
                masterBtn, new DifficultyHandler(), INITIAL_DIFFICULTY.getId());

        beginnerBtn.setListener(difficultyListener);
        normalBtn.setListener(difficultyListener);
        masterBtn.setListener(difficultyListener);
    }

    private class DifficultyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            playMenuButton.setDifficulty(msg.arg1);
        }

    }
}



















