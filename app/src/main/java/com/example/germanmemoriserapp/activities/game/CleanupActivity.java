package com.example.germanmemoriserapp.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import com.example.germanmemoriserapp.utilities.AppCleanup;

class CleanupActivity extends AppCompatActivity {

    AppCleanup appCleaner = new AppCleanup(this,this);

    @Override
    public void onPause() {
        super.onPause();
        appCleaner.run();
    }

    @Override
    public void onStop() {
        super.onStop();
        appCleaner.run();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appCleaner.run();
    }
}
