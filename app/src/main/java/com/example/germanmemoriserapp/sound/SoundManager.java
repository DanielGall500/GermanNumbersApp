package com.example.germanmemoriserapp.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private SoundPool soundPlayer;
    private SoundDirectory soundDir;
    private Context appContext;
    private Queue<SoundElement> waitingSoundElements = new LinkedList<>();
    private ArrayList<SoundElement> loadedSoundElements = new ArrayList<>();
    private Handler onAudioLoadedHandler;

    private SoundManager(Context context) {
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

        this.appContext = context;
        this.soundDir = new SoundDirectory(appContext);
        this.soundPlayer.setOnLoadCompleteListener(new QueueListener());
    }

    /*
    Singleton implementation.
     */
    public static synchronized SoundManager get(Context context) {
        if (soundManager == null) {
            soundManager = new SoundManager(context);
        }

        return soundManager;
    }

    public void setOnAudioLoadedHandler(Handler h) {
        this.onAudioLoadedHandler = h;
    }

    public void load(SoundElement sound) {
        int resId = soundDir.getId(sound);
        int localId = loadClip(resId);

        sound.setLoaded(true);
        sound.setLocalId(localId);

        addToLoadedSounds(sound);
    }

    /* Remove A Clip From Loaded Sounds */
    public void unload(SoundElement element) {

        if (existsInLoadedClips(element)) {
            //Retrieve Element
            int localId = element.getLocalId();

            //Remove & Unload
            removeFromLoadedSounds(element);
            soundPlayer.unload(localId);
        } else {
            invalidElementException();
        }
    }

    public void loadAll(ArrayList<SoundElement> elements) {

        if (elements.size() < 1) {
            throw new IllegalArgumentException("Invalid Elements Arr");
        }

        /*
        Add all to a queue, where they will wait
        to be loaded individually.
         */
        for (SoundElement el : elements) {
            addToWaitingQueue(el);
        }

        loadAllInQueue();
    }

    /* Play A Loaded Clip */
    public void play(SoundElement element) {

        System.out.println("Attempting To Play: " + element.getFileName());

        if (existsInLoadedClips(element)) {
            int localId = element.getLocalId();

            System.out.println("Exists: " + localId);

            soundPlayer.play(localId, LEFT_VOL, RIGHT_VOL,
                    PRIORITY, LOOP, RATE);
        } else {
            invalidElementException();
        }
    }

    public void play(int number) {
        SoundElement element = getLoadedClip(number);
        play(element);
    }

    public SoundElement getElement(int number) {
        if(existsInLoadedClips(number)) {
            return getLoadedClip(number);
        }
        throw new IllegalArgumentException("Invalid Number");
    }

    public ArrayList<SoundElement> getLoadedElements() {
        return this.loadedSoundElements;
    }

    public ArrayList<NumberClip> getLoadedNumbers() {
        ArrayList<NumberClip> numbers = new ArrayList<>();

        for(SoundElement element : loadedSoundElements) {
            if(element.isNumber()) {
                numbers.add((NumberClip) element);
            }
        }
        return numbers;
    }

    public void releaseAllNumbers() {
        for(SoundElement element : loadedSoundElements) {
            if(element.isNumber()) {
                unload(element);
            }
        }
    }

    private void loadAllInQueue() {
        if (waitingSoundElements.size() > 0) {
            load(waitingSoundElements.poll());
        }
    }

    private int loadClip(int resId) {
        return soundPlayer.load(appContext, resId, 1);
    }

    private boolean existsInLoadedClips(String file) {
        SoundElement element = getLoadedClip(file);
        return element != null;
    }

    private boolean existsInLoadedClips(int number) {
        NumberClip num = new NumberClip(number);
        return existsInLoadedClips(num);
    }

    private boolean existsInLoadedClips(SoundElement element) {
        return existsInLoadedClips(element.getFileName());
    }

    private SoundElement getLoadedClip(String file) {
        for (SoundElement nextElement : loadedSoundElements) {

            String nextFileName = nextElement.getFileName();

            if (nextFileName.equals(file)) {
                return nextElement;
            }
        }
        return null;
    }

    private SoundElement getLoadedClip(int number) {
        return getLoadedClip(new NumberClip(number).getFileName());
    }

    /* Waiting & Loaded Sounds */
    private void addToWaitingQueue(SoundElement sound) {
        waitingSoundElements.add(sound);
    }
    private void addToLoadedSounds(SoundElement sound) {
        loadedSoundElements.add(sound);
    }
    private void removeFromLoadedSounds(SoundElement sound) {
        loadedSoundElements.remove(sound);
    }

    private void invalidElementException() {
        throw new IllegalArgumentException("Invalid Sound Element");
    }

    private void alertSoundLoaded() {
        System.out.println("Sending Message to " + onAudioLoadedHandler.toString());
        onAudioLoadedHandler.sendEmptyMessage(0);
    }

    private class QueueListener implements SoundPool.OnLoadCompleteListener {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            System.out.println("New load inside manager");
            alertSoundLoaded();
            loadAllInQueue();
        }
    }
}
