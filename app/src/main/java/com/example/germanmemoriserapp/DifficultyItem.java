package com.example.germanmemoriserapp;

import android.graphics.Color;

public class DifficultyItem {
    private String title;
    private int backgroundImg;

    public DifficultyItem(String t, int image) {
        this.title = t;
        this.backgroundImg = image;
    }

    public String getTitle() {
        return this.title;
    }

    public int getBackgroundImg() {
        return this.backgroundImg;
    }
}
