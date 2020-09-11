package com.example.germanmemoriserapp.utilities;

import com.example.germanmemoriserapp.sound.elements.NumberClip;
import com.example.germanmemoriserapp.sound.elements.SoundElement;

import java.util.ArrayList;
import java.util.Random;

public class NumberGenerator {

    private Random randGen = new Random();

    public ArrayList<SoundElement> generateRandom(int min, int max, int N) {

        ArrayList<SoundElement> generated = new ArrayList<>();
        SoundElement nextNumber;

        for (int i = 0; i < N; i++) {
            int tmp = generateNumber(min, max);

            if (!generated.contains(tmp)) {
                nextNumber = new NumberClip(tmp);
                generated.add(nextNumber);
            } else {
                i--;
            }
        }

        return generated;
    }

    public ArrayList<SoundElement> generateNormal(int min, int max) {
        ArrayList<SoundElement> arr = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            arr.add(new NumberClip(i));
        }

        return arr;
    }

    private int generateNumber(int min, int max) {
        return randGen.nextInt(max - min + 1) + min;
    }
}
