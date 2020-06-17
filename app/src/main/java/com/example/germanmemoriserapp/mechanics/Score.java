package com.example.germanmemoriserapp.mechanics;

public class Score {

    private int currScore = 0;

    public Score() {}

    public void increment()
    {
        currScore++;
    }

    public void increase(int x)
    {
        currScore += x;
    }

    public void decrement()
    {
        currScore--;
    }

    public void decrease(int x)
    {
        currScore -= x;
    }

    public int getScore()
    {
        return this.currScore;
    }


}
