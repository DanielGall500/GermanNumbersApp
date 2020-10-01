package ucd.danielgall.klangapp.mechanics.game_elements;

import android.widget.TextView;

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

    public void decrementWithUIUpdate(TextView t) {
        decrement();
        t.setText(String.valueOf(get()));
    }
}
