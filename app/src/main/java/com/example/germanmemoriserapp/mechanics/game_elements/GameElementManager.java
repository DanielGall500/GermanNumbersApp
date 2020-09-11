package com.example.germanmemoriserapp.mechanics.game_elements;

public abstract class GameElementManager {

    private int nElements;

    public GameElementManager(int l) {
        this.nElements = l;
    }

    public void set(int l) {
        this.nElements = l;
    }

    public int get() {
        return this.nElements;
    }

    public void decrement() {
        if (nElements <= 0) {
            throw new IllegalStateException("Lives Cannot Go Below 0");
        }
        nElements--;
    }
}
