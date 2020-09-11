package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.listeners.NewActivityManager;

public class LearnPageButton extends GeneralButton {

    private int pageToLoad;

    public LearnPageButton(int resId, Context context, AppCompatActivity activity,
                           int pageId) {
        super(resId, context, activity);
        this.pageToLoad = pageId;
    }

    @Override
    protected void onAnimEnd() {
        NewActivityManager manager = new NewActivityManager();
        manager.move(super.getContext(),super.getActivity(),
                false, pageToLoad);
    }
}
