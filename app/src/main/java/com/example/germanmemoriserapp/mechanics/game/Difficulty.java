package com.example.germanmemoriserapp.mechanics.game;

public class Difficulty {

    private static final int beginnerId = 0;
    private static final int intermediateId = 1;
    private static final int masterId = 2;
    private static int invalidId = -1;
    private final String beginnerStr = "BEGINNER";
    private final String normalStr = "NORMAL";
    private final String masterStr = "MASTER";
    private final String invalidStr = "EMPTY SLOT";

    private Level diffLevel;

    public Difficulty(int id) {
        this.diffLevel = getLevel(id);
    }

    public Difficulty(Level l) {
        this.diffLevel = l;
    }

    public static boolean isValid(int id) {
        return (id >= beginnerId && id <= masterId);
    }

    public static Level getLevel(int id) {
        switch (id) {
            case beginnerId:
                return Level.BEGINNER;
            case intermediateId:
                return Level.NORMAL;
            case masterId:
                return Level.MASTER;
            default:
                return Level.NONE;
        }
    }

    public static int getId(Level l) {
        switch (l) {
            case BEGINNER:
                return beginnerId;
            case NORMAL:
                return intermediateId;
            case MASTER:
                return masterId;
            default:
                return -1;
        }
    }

    public Level getDifficulty() {
        return diffLevel;
    }

    public void setDifficulty(Level d) {
        this.diffLevel = d;
    }

    public Level getLevel() {
        return getLevel(getId());
    }

    public int getId() {
        return getId(diffLevel);
    }

    public String toString() {
        switch (diffLevel) {
            case BEGINNER:
                return beginnerStr;
            case NORMAL:
                return normalStr;
            case MASTER:
                return masterStr;
            default:
                return invalidStr;
        }
    }

    public enum Level {
        BEGINNER, NORMAL, MASTER, NONE
    }


}
