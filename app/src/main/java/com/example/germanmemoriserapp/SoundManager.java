package com.example.germanmemoriserapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import java.util.HashMap;

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

    public static synchronized SoundManager get() {
        if(soundManager == null) {
            soundManager = new SoundManager();
        }

        return soundManager;
    }


    /*
    TODO
    Implement a load queue, where each new
    load can be queued and wait its turn for
    loading and removed from the queue once
    loaded.
     */
    private int soundIds[] = new int[] {
            R.raw.nummer_ein, R.raw.nummer_zwei,
            R.raw.nummer_drei, R.raw.nummer_vier,
            R.raw.nummer_funf, R.raw.nummer_sechs,
            R.raw.nummer_sieben, R.raw.nummer_acht,
            R.raw.nummer_neun, R.raw.nummer_zehn
    };

    private SoundPool soundPlayer;
    private boolean isLoaded = false;
    private Handler audioHandler;
    private int loadedSoundsIds[];

    private HashMap<Integer, Integer> soundMap;


    private final int NUM_AUDIO_CLIPS = 10;
    private int numLoadedClips = 0;

    private int currLoad = 0;
    private Context appContext;

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

        loadedSoundsIds = new int[NUM_AUDIO_CLIPS];
        soundMap = new HashMap<>();

        this.soundPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        soundPlayer.setOnLoadCompleteListener(new LoadListener());

    }

    /*
    Load the sounds for the game.
     */
    public void loadGameSounds(Context context) {

        //int id = soundIds[0];
       // GLOB_ID = soundPlayer.load(context, id, 1);

        this.appContext = context;

        //addNumberAudio(1, soundId, context);
        int num = 1;
        loadAudioClip(num, soundIds[currLoad], context);


        //mID = soundPlayer.load(context, soundIds[currLoad], 1);
    }

    private void loadAudioClip(int num, int res, Context context)
    {
        int mID = soundPlayer.load(context, res, 1);
        storeSoundId(num, mID);
    }

    private void storeSoundId(int num, int id) {
        soundMap.put(num, id);
    }

    /*
    Play a sound that has been previously loaded.
     */
    public void play(int num) {

        if(!isValid(num))
            throw new IllegalArgumentException("Invalid Number");

        int id = getLoadedSoundId(num);

        System.out.println("Attempting To Play Sound ID: " + id);
        System.out.println("Number: " + num);

        soundPlayer.play(id, 1, 1, 0, -1, 1);
    }

    public void reset() {

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
     */
    private void alertSoundLoaded(int id) {
        numLoadedClips++;

        if(allClipsLoaded())
            onAllLoadsComplete();
    }

    /*
    Check if every sound clip needed for this
    particular game has loaded.
     */
    private boolean allClipsLoaded() {
        return getNumLoadedClips() == NUM_AUDIO_CLIPS;
    }

    private void onAllLoadsComplete() {
        /*Tell the loading screen that our audio
          files are ready*/

        if(audioHandler == null) {
            throw new RuntimeException("Handler Must Be Set");
        }

        audioHandler.sendEmptyMessage(0);
    }

    public void destroy() {
        soundPlayer.release();
        soundPlayer = null;
    }

    private int getLoadedSoundId(int num) {
        if(!soundMap.containsKey(num))
            throw new IllegalArgumentException("Invalid Number");

        return soundMap.get(num);
    }

    private boolean isLoaded() {
        return this.isLoaded;
    }
    public int getNumLoadedClips() {
        return this.numLoadedClips;
    }
    private boolean isValid(int num) {
        return soundMap.containsKey(num);
    }

    class LoadListener implements SoundPool.OnLoadCompleteListener {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            /*
            because i have a for loop loading in everything
            asynchronously, its not loading the ones i want first?
             */

            soundPlayer.play(getLoadedSoundId(currLoad+1), 1, 1, 0, -1, 1);

            if(++currLoad < NUM_AUDIO_CLIPS)
                loadAudioClip(currLoad+1,soundIds[currLoad],appContext);
        }

    }


}
