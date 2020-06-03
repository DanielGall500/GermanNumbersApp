package com.example.germanmemoriserapp;

import java.util.ArrayList;

public class Game {

    private NumSupplier generator;
    private Score playerScore = new Score();

    private int currentNumber;

    private int TURN = 0;
    private int NUM_TURNS;

    public Game(int turns)
    {
        this.NUM_TURNS = turns;

        generator = new NumSupplier(1, 10, NUM_TURNS);

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

    public void parse(int input)
    {
        updateScore(input);
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
            TURN++;
            currentNumber = generator.next();
        }
    }

}
