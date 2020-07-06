package com.example.germanmemoriserapp.mechanics;

public class Difficulty {

    private static int INFINITE = -1;

    private final int BEGINNER_MIN = 0;
    private final int BEGINNER_MAX = 100;

    private final int NORMAL_MIN = 100;
    private final int NORMAL_MAX = 1000;

    private final int MASTER_MIN = 1000;
    private final int MASTER_MAX = 10000;

    public enum Level {
        BEGINNER, NORMAL, MASTER
    };

    private Level diffLevel;

    public Difficulty(Level l) {
        this.diffLevel = l;
    }

    public void setDifficulty(Level d) {
        this.diffLevel = d;
    }

    public Level getDifficulty() {
        return diffLevel;
    }

    public int getMin() {
        switch(diffLevel) {
            case BEGINNER:
                return BEGINNER_MIN;
            case NORMAL:
                return NORMAL_MIN;
            default:
                return MASTER_MIN;
        }
    }

    public int getMax() {
        switch(diffLevel) {
            case BEGINNER:
                return BEGINNER_MAX;
            case NORMAL:
                return NORMAL_MAX;
            default:
                return MASTER_MAX;
        }
    }

}
