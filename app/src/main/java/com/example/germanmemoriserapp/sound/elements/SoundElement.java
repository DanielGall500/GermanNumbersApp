package com.example.germanmemoriserapp.sound.elements;

public interface SoundElement {

    String getFileName();

    boolean isLoaded();

    void setLoaded(boolean isLoaded);

    int getLocalId();

    void setLocalId(int id);

    boolean hasLocalId();

    boolean isNumber();
}
