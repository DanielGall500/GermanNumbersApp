package com.example.germanmemoriserapp.mechanics;

public class Score {

    private int currScore = 0;

    public Score() {}

    public void increment()
    {
        currScore++;
    }

    public int getScore()
    {
        return this.currScore;
    }


}
