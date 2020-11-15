package ucd.danielgall.klangapp.sound;

import ucd.danielgall.klangapp.sound.elements.SoundElement;

public interface SoundPlayer {
    void play(SoundElement element);
    void play(int number);
}
