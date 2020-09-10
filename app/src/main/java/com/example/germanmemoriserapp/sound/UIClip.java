package com.example.germanmemoriserapp.sound;

/*
Used as an ID for which sound element
we would like to load in.
 */
public enum UIClip implements SoundElement {
    GENERAL_BUTTON_CLICK,
    GAME_CORRECT,
    GAME_INCORRECT,
    GAME_WON,
    GAME_LOST;

    private int localStorageId = -1;
    private boolean isLoaded = false;

    final String GENERAL_BUTTON_CLICK_STR = "button_click";
    final String GAME_LOST_STR = "game_lost";
    final String GAME_WON_STR = "game_won";
    final String GAME_CORRECT_STR = "game_correct_click";
    final String GAME_INCORRECT_STR = "game_incorrect_click";

    @Override
    public String getFileName() {
        switch(this) {
            case GENERAL_BUTTON_CLICK:
                return GENERAL_BUTTON_CLICK_STR;
            case GAME_CORRECT:
                return GAME_CORRECT_STR;
            case GAME_INCORRECT:
                return GAME_INCORRECT_STR;
            case GAME_WON:
                return GAME_WON_STR;
            case GAME_LOST:
                return GAME_LOST_STR;
            default:
                return null;
        }
    }

    public boolean isNumber() {
        return false;
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
}
