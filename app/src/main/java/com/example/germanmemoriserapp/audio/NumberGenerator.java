package com.example.germanmemoriserapp.audio;

import java.util.ArrayList;
import java.util.Random;

public class NumberGenerator {

    private Random randGen;

    public NumberGenerator() {
        randGen = new Random();
    }

    protected ArrayList<Integer> generateArray(int min, int max, int size) {
        ArrayList<Integer> generated = new ArrayList<>();

        int tmp;
        for (int i = 0; i < size; i++) {
            tmp = generateNumber(min, max);
            generated.add(tmp);
        }

        return generated;
    }

    int generateNumber(int min, int max) {
        return randGen.nextInt(max - min + 1) + min;
    }
}