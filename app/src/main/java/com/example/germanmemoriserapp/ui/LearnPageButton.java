package com.example.germanmemoriserapp.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;

public class LearnPageButton extends GeneralButton {

    private int pageToLoad;

    public LearnPageButton(Context context, AppCompatActivity activity,
                           int resId, int pageId) {
        super(context, activity, resId);
        this.pageToLoad = pageId;
    }

    @Override
    protected void onAnimEnd() {
        NextActivityManager manager = new NextActivityManager(getContext(),getActivity());

        String key = getContext().getString(R.string.score_load_intent_key);

        manager.addInformation(key, pageToLoad);
    }
}
