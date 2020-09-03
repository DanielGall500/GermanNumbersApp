package com.example.germanmemoriserapp.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import com.example.germanmemoriserapp.mechanics.Difficulty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class SoundManager {

    //Singleton Sound Manager Object
    private static SoundManager soundManager;
    /*
    Audio Settings
     */
    private final int MAX_STREAMS = 1;
    private final int SRC_QUALITY = 1;
    private final int STREAM_TYPE = AudioManager.STREAM_MUSIC;
    /*
    Audio Attributes
     */
    private final int ATT_USAGE = AudioAttributes.USAGE_ASSISTANCE_SONIFICATION;
    private final int ATT_FLAG = AudioAttributes.FLAG_AUDIBILITY_ENFORCED;
    private final int ATT_CONTENT = AudioAttributes.CONTENT_TYPE_SONIFICATION;
    /*
    Playback Settings
     */
    private final int LEFT_VOL = 1;
    private final int RIGHT_VOL = 1;
    private final int PRIORITY = 1;
    private final int LOOP = 0;
    private final int RATE = 1;

    private SoundDirectory directories;
    private SoundPool soundPlayer;
    private Handler audioHandler;
    private Handler progressHandler;
    private HashMap<Integer, Integer> soundMap;
    private Queue<Integer> soundIdQueue;
    private ArrayList<Integer> numberArray;
    private int soundIterator;
    private Context appContext;

    private SoundManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(ATT_USAGE).setFlags(ATT_FLAG)
                    .setContentType(ATT_CONTENT).build();

            soundPlayer = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPlayer = new SoundPool(MAX_STREAMS, STREAM_TYPE, SRC_QUALITY);
        }

        soundMap = new HashMap<>();
        directories = new SoundDirectory(appContext);
        numberArray = new ArrayList<>();

        soundPlayer.setOnLoadCompleteListener(new LoadListener());
    }

    /*
    Singleton implementation.
     */
    public static synchronized SoundManager get() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }

        return soundManager;
    }

    /*
    Generate new numbers (audio clips) for a new game.
    */
    public void init(boolean isGame, int info, Context context) {

        this.appContext = context;

        /*
        Start from the first sound.
         */
        soundIterator = 0;


        if(isGame) {
            /*
            Generate a set of file IDs for number in this
            difficulty level.
             */
            soundIdQueue = directories.generateIds(context, info);
            numberArray = directories.getIntegerArray();
        }
        else {
            //TODO
            soundIdQueue = directories.loadIds(1,10, context);
        }

        /*
        Load audio clips on a new thread
         */
        Thread t = new Thread() {
            @Override
            public void run() {
                loadAll();
            }
        };

        t.start();
    }

    public boolean hasNext() {
        return (soundIterator + 1) <= numberArray.size();
    }

    public int next() {
        if (!hasNext())
            throw new RuntimeException("No Numbers Left");
        else
            return numberArray.get(soundIterator++);
    }

    /*
    Play a sound that has been previously loaded.
     */
    public void play(int num) {

        if (!isLoaded(num))
            throw new IllegalArgumentException("Invalid Number");

        int id = getLoadedSoundId(num);
        soundPlayer.play(id, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void setLoadCompleteHandler(Handler h) {
        this.audioHandler = h;
    }

    public void setProgressHandler(Handler h) {
        this.progressHandler = h;
    }

    /*
    Load all sounds needed for the game.
    */
    private void loadAll() {

        if (soundIdQueue.isEmpty())
            throw new RuntimeException("No Game Directories Prepared");

        int soundId = soundIdQueue.poll();
        int num = directories.getNum(soundId);

        loadAudioClip(num, soundId);
    }

    /*
    Load a specific number clip.
     */
    private void loadAudioClip(int num, int res) {
        int mID = soundPlayer.load(appContext, res, 1);
        storeSoundId(num, mID);
    }

    private void storeSoundId(int num, int id) {
        soundMap.put(num, id);
    }

    /*
    If a new audio clip is loaded, we handle it by calling
    to the alertSoundLoaded method.

    If there are more clips in the sound queue, it
    automatically loads the next one.

    Otherwise if all sounds are loaded, we make sure
    to send out a signal telling the rest of our
    application.
     */
    private void alertSoundLoaded() {
        if (!soundIdQueue.isEmpty()) {
            int nextDir = soundIdQueue.poll();
            int number = directories.getNum(nextDir);

            loadAudioClip(number, nextDir);
        } else {
            onAllLoadsComplete();
        }
    }

    /*
    Tell the loading screen that our audio
    files are ready
    */
    private void onAllLoadsComplete() {
        if (audioHandler != null) {
            audioHandler.sendEmptyMessage(0);
        }
    }

    /*
    Retrieve the id of our loaded sound for
    playback. Note that this is NOT the same
    as an id for the directory of each sound.
     */
    private int getLoadedSoundId(int num) {
        if (!soundMap.containsKey(num))
            throw new IllegalArgumentException("Invalid Number");

        return soundMap.get(num);
    }

    private boolean isLoaded(int num) {
        return soundMap.containsKey(num);
    }

    private void alertProgressBarUpdate() {
        progressHandler.sendEmptyMessage(0);
    }

    /*
    Called every time a new audio clip is loaded.
    If there are more clips in the sound queue, the
    listener automatically loads the next one.
     */
    class LoadListener implements SoundPool.OnLoadCompleteListener {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            alertSoundLoaded();
            alertProgressBarUpdate();
        }
    }


}
