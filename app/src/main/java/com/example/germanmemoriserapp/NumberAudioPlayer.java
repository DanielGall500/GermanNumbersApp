package com.example.germanmemoriserapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class NumberAudioPlayer implements Parcelable {

    private int NUM_AUDIO_CLIPS = 10;

    private int soundIds[] = new int[] {
            R.raw.nummer_ein, R.raw.nummer_zwei,
            R.raw.nummer_drei, R.raw.nummer_vier,
            R.raw.nummer_funf, R.raw.nummer_sechs,
            R.raw.nummer_sieben, R.raw.nummer_acht,
            R.raw.nummer_neun, R.raw.nummer_zehn
    };

    private SoundPool soundPlayer;
    private Context context;

    private boolean isLoaded = false;
    private int soundID;

    private int numLoadedClips = 0;

    private Handler audioHandler;

    private int loadedSoundsIds[];

    public NumberAudioPlayer(Context context, int maxStreams, int streamType, int srcQuality, Handler h) {
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
            soundPlayer = new SoundPool(maxStreams, streamType, srcQuality);
        }

        this.audioHandler = h;
        this.context = context;
        this.loadedSoundsIds = new int[NUM_AUDIO_CLIPS];
        this.soundPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        soundPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                numLoadedClips++;

                if(allClipsLoaded()) {
                    onAllLoadsComplete();
                }
            }
        });

        for(int i = 1; i <= 10; i++) {
            addNumberAudio(i, soundIds[i-1]);
        }
    }

    private boolean allClipsLoaded()
    {
        return getNumLoadedClips() == NUM_AUDIO_CLIPS;
    }

    private void onAllLoadsComplete()
    {
        /*Tell the loading screen that our audio
          files are ready*/

        audioHandler.sendEmptyMessage(0);
    }

    public void destroy() {
        soundPlayer.release();
        soundPlayer = null;
    }

    public int getNumLoadedClips() {
        return this.numLoadedClips;
    }

    private void addNumberAudio(int num, int soundID)
    {
        int loadClip = soundPlayer.load(context, soundID, 1);
        addLoadedSoundId(num, soundID);
    }

    private void addLoadedSoundId(int num, int id) {
        loadedSoundsIds[num-1] = id;
    }

    private int getLoadedSoundId(int num) {
        if(num < 1 || num > NUM_AUDIO_CLIPS)
            throw new IllegalArgumentException("Invalid Number");

        return loadedSoundsIds[num-1];
    }

    private boolean isLoaded() {
        return this.isLoaded;
    }

    private boolean isValidNum(int num) {
        return !(num < 1 || num > NUM_AUDIO_CLIPS);
    }

    public void play(int num) {

        if(!isValidNum(num))
            throw new IllegalArgumentException("Invalid Number");

        int id = getLoadedSoundId(num);

        System.out.println("Attempting To Play Sound ID: " + id);
        System.out.println("Number: " + num);

        soundPlayer.play(id, 1, 1, 0, 0, 1);
    }

    protected NumberAudioPlayer(Parcel in) {
        NUM_AUDIO_CLIPS = in.readInt();
        soundIds = in.createIntArray();
        isLoaded = in.readByte() != 0;
        soundID = in.readInt();
        numLoadedClips = in.readInt();
        loadedSoundsIds = in.createIntArray();
    }

    public static final Creator<NumberAudioPlayer> CREATOR = new Creator<NumberAudioPlayer>() {
        @Override
        public NumberAudioPlayer createFromParcel(Parcel in) {
            return new NumberAudioPlayer(in);
        }

        @Override
        public NumberAudioPlayer[] newArray(int size) {
            return new NumberAudioPlayer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(NUM_AUDIO_CLIPS);
        dest.writeIntArray(soundIds);
        dest.writeByte((byte) (isLoaded ? 1 : 0));
        dest.writeInt(soundID);
        dest.writeInt(numLoadedClips);
        dest.writeIntArray(loadedSoundsIds);
    }
}
