package com.example.germanmemoriserapp.audio;

import android.content.Context;
import android.media.SoundPool;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Clip {

    private String clipNumber;
    private int nDigits;
    private ArrayList<Integer> units;
    private ArrayList<Integer> soundIds;

    private SoundDirectory DIR;

    private boolean isLoaded;

    public Clip(String number, SoundDirectory dir) {

        if(!isValid(number)) {
            throw new IllegalArgumentException("Clip Unavailable");
        }


        this.DIR = dir;
        this.clipNumber = number;
        this.nDigits = clipNumber.length();
        this.soundIds = new ArrayList<>();

        for(int num : splitInteger(clipNumber, nDigits)) {
            int id = DIR.getId(num);

            soundIds.add(id);
        }
    }

    public ArrayList<Integer> getNecessaryFiles() {
        return soundIds;
    }

    public void play() {
        return;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    private boolean isValid(String input) {
        return true;
    }

    private ArrayList<Integer> splitInteger(String INP, int N) {

        ArrayList<Integer> splitArr = new ArrayList<>();

        for(int i = 0; i < N; i++) {

            //Get Next Character
            int nextDigit = INP.charAt(i) - 48;
            System.out.println(nextDigit);

            //Number x 10^(N-i)
            int units = (int) (nextDigit * Math.pow(10, N - i - 1));

            //Add To Our Array
            splitArr.add(units);
        }



        return splitArr;
    }

}
