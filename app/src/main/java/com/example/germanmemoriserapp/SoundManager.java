package com.example.germanmemoriserapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/*
ONLY LOAD NUMBERS AFTER YOU KNOW WHICH ONES
ARE BEING USED
 */

public class SoundManager {

    //Holds our one instance of the sound manager
    //for global use
    private static SoundManager soundManager;

    private final int MAX_STREAMS = 1;
    private final int SRC_QUALITY = 1;
    private final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

    private final int LEFT_VOL = 1;
    private final int RIGHT_VOL = 1;
    private final int PRIORITY = 1;
    private final int LOOP = -1;
    private final int RATE = 1;

    private NumSupplier numSupplier;
    private SoundDirectory directories;
    private SoundPool soundPlayer;
    private Handler audioHandler;
    private HashMap<Integer, Integer> soundMap;
    private Queue<Integer> soundIdQueue;

    private ArrayList<Integer> numberArray;
    private int soundIterator = 0;

    private Context appContext;

    /*
    Called every time a new audio clip is loaded.
    If there are more clips in the sound queue, the
    listener automatically loads the next one.
     */
    class LoadListener implements SoundPool.OnLoadCompleteListener {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            alertSoundLoaded();
        }
    }

    private SoundManager() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPlayer = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPlayer = new SoundPool(MAX_STREAMS, STREAM_TYPE, SRC_QUALITY);
        }

        soundMap = new HashMap<>();

        //REMOVE THIS ?
        this.soundPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        soundPlayer.setOnLoadCompleteListener(new LoadListener());

        numSupplier = new NumSupplier();
        directories = new SoundDirectory();
    }

    /*
    Singleton implementation.
     */
    public static synchronized SoundManager get() {
        if(soundManager == null) {
            soundManager = new SoundManager();
        }

        return soundManager;
    }

    public void init(int min, int max, int size, Context context) {

        this.appContext = context;

        this.numberArray = numSupplier.generate(min, max, size);

        soundIdQueue = directories.getResIds(numberArray, appContext);

        loadAll();
    }

    public void reset() {
        soundPlayer.release();
        soundPlayer = null;

        directories.reset();
    }

    /*
    Load all sounds needed for the game.
     */
    public void loadAll() {

        if(soundIdQueue.isEmpty())
            throw new RuntimeException("No Game Directories Prepared");

        int soundId = soundIdQueue.poll();
        int num = directories.getNum(soundId);

        loadAudioClip(num, soundId);
    }

    private void loadAudioClip(int num, int res) {
        int mID = soundPlayer.load(appContext, res, 1);
        storeSoundId(num, mID);
    }

    private void storeSoundId(int num, int id) {
        soundMap.put(num, id);
    }

    /*
    Play a sound that has been previously loaded.
     */
    public void play(int num) {

        if(!isLoaded(num))
            throw new IllegalArgumentException("Invalid Number");

        int id = getLoadedSoundId(num);
        soundPlayer.play(id, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void setLoadCompleteHandler(Handler h) {
        this.audioHandler = h;
    }

    public Handler getLoadCompleteHandler() {
        return this.audioHandler;
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
        if(!soundIdQueue.isEmpty()) {
            int nextDir = soundIdQueue.poll();
            int number = directories.getNum(nextDir);

            loadAudioClip(number, nextDir);
        } else {
            onAllLoadsComplete();
        }
    }

    /*
    Check if every sound clip needed for this
    particular game has loaded.
     */
    private boolean allClipsLoaded() {
        return soundIdQueue.isEmpty();
    }

    /*
    Tell the loading screen that our audio
    files are ready
    */
    private void onAllLoadsComplete() {
        if(audioHandler != null) {
            audioHandler.sendEmptyMessage(0);
        }
    }

    public void init(ArrayList<Integer> nums) {
        this.numberArray = nums;
    }

    /*
    Retrieve the id of our loaded sound for
    playback. Note that this is NOT the same
    as an id for the directory of each sound.
     */
    private int getLoadedSoundId(int num) {
        if(!soundMap.containsKey(num))
            throw new IllegalArgumentException("Invalid Number");

        return soundMap.get(num);
    }

    private boolean isLoaded(int num) {
        return soundMap.containsKey(num);
    }

    public void resetIter() {
        soundIterator = 0;
    }

    public boolean hasNext() {
        return (soundIterator + 1) < numberArray.size();
    }

    public int next() {
        if(!hasNext())
            throw new RuntimeException("No Numbers Left");
        else
            return numberArray.get(soundIterator++);
    }



}
