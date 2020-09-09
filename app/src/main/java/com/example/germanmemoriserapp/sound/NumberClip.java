package com.example.germanmemoriserapp.sound;

public class NumberClip implements SoundElement {

    private int numberClip;
    private boolean isLoaded = false;
    private final String NUMBER_PREFIX = "nummer_";

    private int localStorageId = -1;

    public NumberClip(int number) {
        this.numberClip = number;
    }

    public boolean isNumber() {
        return true;
    }

    public int get() {
        return this.numberClip;
    }

    @Override
    public String getFileName() {
        return getNumberFileName(numberClip);
    }

    @Override
    public boolean isLoaded() {
        return this.isLoaded;
    }

    @Override
    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public int getLocalId() {
        return this.localStorageId;
    }

    @Override
    public void setLocalId(int id) {
        this.localStorageId = id;
    }

    @Override
    public boolean hasLocalId() {
        return localStorageId != -1;
    }

    private String getNumberFileName(int number) {
        return String.format("%s%d", NUMBER_PREFIX, number);
    }
}
