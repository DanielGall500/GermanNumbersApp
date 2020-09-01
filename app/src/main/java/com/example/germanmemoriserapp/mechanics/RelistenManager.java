package com.example.germanmemoriserapp.mechanics;

import android.widget.TextView;

public class RelistenManager extends GameElementManager {
    public RelistenManager(int nRelistens) {
        super(nRelistens);
    }

    public boolean isOutOfListens() {
        return (this.get() == 0);
    }

    public void decrementWithUIUpdate(TextView t) {
        decrement();
        t.setText(String.valueOf(get()));
    }
}
