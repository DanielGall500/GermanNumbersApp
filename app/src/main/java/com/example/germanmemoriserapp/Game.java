package com.example.germanmemoriserapp;

import java.util.ArrayList;

public class Game {

    private NumSupplier generator;
    private Score playerScore = new Score();

    private int currentNumber;

    private int TURN = 0;
    private int NUM_TURNS;

    public Game(int min, int max, int turns)
    {
        this.NUM_TURNS = turns;

        generator = new NumSupplier(min, max, turns);

        newTurn();
    }

    public int getNumber()
    {
        return currentNumber;
    }

    public int getScore()
    {
        return playerScore.getScore();
    }

    public boolean play(int input)
    {
        if(!generator.isCorrectNumber(input))
            return false;

        updateScore(input);
        return true;
    }

    public boolean isValidInput(String input)
    {
        if(input.length() == 0)
            return false;

        char[] chars = input.toCharArray();
        int digit;

        for(char c : chars)
        {
            digit = ((int)c - 48);

            if(digit < 0 || digit > 9)
                return false;
        }
        return true;
    }

    private void updateScore(int input)
    {
        if(generator.isCorrectNumber(input))
            playerScore.increment();
    }

    public boolean isEndOfGame()
    {
        return (TURN == NUM_TURNS);
    }

    public void newTurn()
    {
        if(isEndOfGame())
        {
            throw new RuntimeException("EOG Reached");
        }
        else
        {
            incrementTurn();
            nextNumber();
        }
    }

    private void nextNumber()
    {
        currentNumber = generator.next();
    }

    private void incrementTurn()
    {
        TURN++;
    }

}
