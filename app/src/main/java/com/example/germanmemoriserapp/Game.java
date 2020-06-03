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

    public void play(int input)
    {
        updateScore(input);
    }

    private void updateScore(int input)
    {
        System.out.println("Is CorrectL : " + generator.isCorrectNumber(input));
        System.out.println("Input : " + input);
        System.out.println("Actual: " + generator.getCurrentNumber());

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

            System.out.println("Curr: " + currentNumber);
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
