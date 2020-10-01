package ucd.danielgall.klangapp.sound;

import android.content.Context;
import ucd.danielgall.klangapp.sound.elements.NumberClip;
import ucd.danielgall.klangapp.sound.elements.SoundElement;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.sound.elements.UIClip;

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
    private final int STREAM_TYPE = AudioAttributes.USAGE_MEDIA;
    /*
    Audio Attributes
     */
    private final int ATT_USAGE = AudioAttributes.USAGE_GAME;
    private final int ATT_FLAG = AudioAttributes.FLAG_AUDIBILITY_ENFORCED;
    private final int ATT_CONTENT = AudioAttributes.CONTENT_TYPE_SONIFICATION;
    /*
    Playback Settings
     */
    private final int PRIORITY = 1;
    private final int LOOP = 0;
    private final int RATE = 1;
    private SoundPool soundPlayer;
    private SoundDirectory soundDir;
    private Context appContext;
    private AppCompatActivity appActivity;
    private Queue<SoundElement> waitingSoundElements = new LinkedList<>();
    private ArrayList<SoundElement> loadedSoundElements = new ArrayList<>();
    private Handler onAudioLoadedHandler;
    private AudioManager audioManager;

    private SoundManager(Context context, AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(ATT_USAGE)
                    .setFlags(ATT_FLAG)
                    .setContentType(ATT_CONTENT)
                    .build();

            soundPlayer = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPlayer = new SoundPool(MAX_STREAMS, STREAM_TYPE, SRC_QUALITY);
        }

        this.appContext = context;
        this.appActivity = activity;
        this.soundDir = new SoundDirectory(appContext);
        this.soundPlayer.setOnLoadCompleteListener(new QueueListener());
        this.audioManager = (AudioManager) appActivity.getSystemService(Context.AUDIO_SERVICE);

        appActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    /*
    Singleton implementation.
     */
    public static synchronized SoundManager get(Context context, AppCompatActivity activity) {
        if (soundManager == null) {
            soundManager = new SoundManager(context, activity);
        }

        return soundManager;
    }

    public static boolean isActive() {
        return soundManager != null;
    }

    public void release() {
        soundManager = null;
        soundPlayer.release();
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

    public void unloadAll(ArrayList<SoundElement> elements) {
        for (SoundElement element : elements) {
            unload(element);
        }
    }

    /* Play A Loaded Clip */
    public void play(SoundElement element) {
        if (existsInLoadedClips(element)) {
            int localId = element.getLocalId();
            float volume = getVolume();

            soundPlayer.play(localId, volume, volume,
                    PRIORITY, LOOP, RATE);
        }
    }

    private float getVolume() {
        float streamVolumeCurrent = audioManager.getStreamVolume(
                appActivity.getVolumeControlStream());

        float streamVolumeMax = audioManager.getStreamMaxVolume(
                appActivity.getVolumeControlStream());

        return streamVolumeCurrent / streamVolumeMax;
    }

    public void play(int number) {
        SoundElement element = getLoadedClip(number);
        play(element);
    }

    public ArrayList<NumberClip> getLoadedNumbers() {
        ArrayList<NumberClip> numbers = new ArrayList<>();

        for (SoundElement element : loadedSoundElements) {
            if (element.isNumber()) {
                numbers.add((NumberClip) element);
            }
        }
        return numbers;
    }

    public void releaseAllNumberClips() {
        ArrayList<SoundElement> elementsToUnload = new ArrayList<>();

        for (SoundElement element : loadedSoundElements) {
            if (element.isNumber()) {
                elementsToUnload.add(element);
            }
        }

        unloadAll(elementsToUnload);
    }

    public void releaseAllGameUIClips() {
        unload(UIClip.GAME_WON);
        unload(UIClip.GAME_LOST);
        unload(UIClip.GAME_CORRECT);
        unload(UIClip.GAME_INCORRECT);
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
        if (element == null) {
            return false;
        }

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

    private void alertSoundLoaded() {
        onAudioLoadedHandler.sendEmptyMessage(0);
    }

    private class QueueListener implements SoundPool.OnLoadCompleteListener {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            alertSoundLoaded();
            loadAllInQueue();
        }
    }
}
