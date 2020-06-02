package com.example.germanmemoriserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String enterNumberTxt = "Got it!";

    EditText enterNumberBox;
    Button enterButton;

    Game GAME = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterNumberBox = findViewById(R.id.enterNumberBox);

        enterButton = findViewById(R.id.enterButton);
        enterButton.setText(enterNumberTxt);

        enterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String strInput = enterNumberBox.getText().toString();

                int INPUT = Integer.parseInt(strInput);

                GAME.enterInput(INPUT);
                enterNumberBox.setText("");
            }
        });


    }
}
