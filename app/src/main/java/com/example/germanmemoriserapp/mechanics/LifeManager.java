package com.example.germanmemoriserapp.mechanics;

public class LifeManager extends GameElementManager {
    public LifeManager(int nLives) {
        super(nLives);
    }

    public boolean hasLives() {
        return (this.get() > 0);
    }

    public boolean isOutOfLives() {
        return (this.get() == 0);
    }
}
