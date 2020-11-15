package ucd.danielgall.klangapp.activities.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ucd.danielgall.klangapp.R;
import ucd.danielgall.klangapp.activity_managers.NextActivityManager;
import ucd.danielgall.klangapp.sound.elements.SoundElement;
import ucd.danielgall.klangapp.sound.elements.UIClip;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private final Class nextActivity = MenuScreen.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
        finish();
    }
}
