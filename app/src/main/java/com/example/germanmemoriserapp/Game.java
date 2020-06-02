package com.example.germanmemoriserapp;

public class Game {

    NumSupplier generator = new NumSupplier();

    private int currentNumber;

    private int TURN = 0;

    public Game() {}

    public int getNextNumber()
    {
        currentNumber = generator.next();
        System.out.println(currentNumber);
        return generator.next();
    }

    public int getCurrentNumber()
    {
        return currentNumber;
    }

    public void enterInput(int input)
    {
        //do something with score
    }
}
