package com.example.germanmemoriserapp;

import java.util.ArrayList;
import java.util.Random;

public class NumSupplier {

    private int DEFAULT_MIN = 0;
    private int DEFAULT_MAX = 10;
    private int DEFAULT_SIZE = 3;

    private int min;
    private int max;
    private int size;

    private int iterator = -1;

    private ArrayList<Integer> genList;
    private Random randGen = new Random();

    public NumSupplier()
    {
        this.min = DEFAULT_MIN;
        this.max = DEFAULT_MAX;
        this.size = DEFAULT_SIZE;

        genList = generateList(min, max, size);
    }

    public NumSupplier(int min, int max, int size)
    {
        this.min = min;
        this.max = max;
        this.size = size;

        genList = generateList(min, max, size);
    }

    public void setMin(int min)
    {
        this.min = min;
        genList = generateList(min, max, size);
    }

    public void setMax(int max)
    {
        this.max = max;
        genList = generateList(min, max, size);
    }

    public void setSize(int size)
    {
        this.size = size;
        genList = generateList(min, max, size);
    }

    public int getCurrentNumber()
    {
        return genList.get(iterator);
    }

    public boolean isEndOfList()
    {
        return (iterator == size-1);
    }

    public int next()
    {
        if(isEndOfList())
            throw new ArrayIndexOutOfBoundsException("Reached End Of List");
        else
            return genList.get(++iterator);
    }

    public boolean isCorrectNumber(int num)
    {
        if(!gameHasBegun())
            throw new ArrayIndexOutOfBoundsException("Must Begin Game Before Calling");
        else
            return num == genList.get(iterator);
    }

    private ArrayList<Integer> generateList(int min, int max, int size)
    {
        ArrayList<Integer> generatedList = new ArrayList<>();

        int tmp;
        for(int i = 0; i < size; i++)
        {
            tmp = generateNumber(min, max);
            generatedList.add(tmp);
        }

        return generatedList;
    }

    private int generateNumber(int min, int max)
    {
        return randGen.nextInt(max - min + 1) + min;
    }

    private boolean gameHasBegun()
    {
        return iterator >= 0;
    }
}
