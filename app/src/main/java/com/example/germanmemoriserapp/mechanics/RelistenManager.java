package com.example.germanmemoriserapp.mechanics;

public class RelistenManager extends GameElementManager {
    public RelistenManager(int nRelistens) {
        super(nRelistens);
    }

    public boolean isOutOfListens() {
        return (this.get() == 0);
    }
}
