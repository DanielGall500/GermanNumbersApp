package com.example.germanmemoriserapp.mechanics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class NumSupplier {

    private int min;
    private int max;
    private int size;

    private ArrayList<Integer> genArray;

    private Random randGen = new Random();

    public NumSupplier() {}

    public ArrayList<Integer> getAll() {
        return this.genArray;
    }

    public void setMin(int min) {
        this.min = min;
        genArray = generateArray(min, max, size);
    }

    public void setMax(int max) {
        this.max = max;
        genArray = generateArray(min, max, size);
    }

    public void setSize(int size) {
        this.size = size;
        genArray = generateArray(min, max, size);
    }

    public ArrayList<Integer> generate(int min, int max, int size) {
        this.min = min;
        this.max = max;
        this.size = size;

        return generateArray(min, max, size);
    }

    private ArrayList<Integer> generateArray(int min, int max, int size) {
        ArrayList<Integer> generated = new ArrayList<>();

        int tmp;
        for(int i = 0; i < size; i++)
        {
            tmp = generateNumber(min, max);
            generated.add(tmp);
        }

        return generated;
    }

    private int generateNumber(int min, int max) {
        return randGen.nextInt(max - min + 1) + min;
    }
}
