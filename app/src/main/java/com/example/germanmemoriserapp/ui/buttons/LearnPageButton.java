package com.example.germanmemoriserapp.ui.buttons;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.R;
import com.example.germanmemoriserapp.activities.load.LoadAudioScreen;
import com.example.germanmemoriserapp.activity_managers.NextActivityManager;

public class LearnPageButton extends GeneralButton {

    private int pageToLoad;
    private Class nextScreen = LoadAudioScreen.class;
    private Context appContext;
    private AppCompatActivity appActivity;

    public LearnPageButton(Context context, AppCompatActivity activity,
                           int resId, int pageId) {
        super(context, activity, resId);
        this.pageToLoad = pageId;
        this.appContext = context;
        this.appActivity = activity;
    }

    @Override
    protected void onAnimEnd() {
        NextActivityManager manager = new NextActivityManager(getContext(), getActivity());
        manager.setNextActivity(nextScreen);

        String scoreLoadKey = getContext().getString(R.string.load_screen_information);
        manager.addInformation(scoreLoadKey, pageToLoad);

        String isGameKey = appActivity.getString(R.string.load_screen_isGameBoolean);
        manager.addInformation(isGameKey, false);

        manager.run();
    }
}
