package com.example.germanmemoriserapp.utilities;

import android.content.Context;
import android.util.Log;

import com.example.germanmemoriserapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NumberFileManager {

    final int MIN = 0;
    final int MAX = 100;
    final String csvSep = ",";
    ArrayList<String> numbersEng;
    ArrayList<String> numbersGer;
    ArrayList<String> numbersDigits;
    ArrayList<String> numbersAudioFiles;

    public NumberFileManager(Context context, boolean loadEnglish,
                             boolean loadGerman, boolean loadDigits,
                             boolean loadAudioFileNames) {

        InputStream iStream = context.getResources().openRawResource(
                R.raw.klang_number_information);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(iStream, StandardCharsets.UTF_8));

        if (loadEnglish)
            numbersEng = new ArrayList<>();

        if (loadGerman)
            numbersGer = new ArrayList<>();

        if (loadDigits)
            numbersDigits = new ArrayList<>();

        if (loadAudioFileNames)
            numbersAudioFiles = new ArrayList<>();

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(csvSep);

                //Digits Eg: 1
                if (loadDigits)
                    numbersDigits.add(tokens[0]);

                //English Numbers Eg: One
                if (loadEnglish)
                    numbersEng.add(tokens[1]);

                //German Numbers Eg: Ein
                if (loadGerman)
                    numbersGer.add(tokens[2]);

                //Audio Files Eg: file_number_1.wav
                if (loadAudioFileNames)
                    numbersAudioFiles.add(tokens[3]);

            }
        } catch (IOException e) {
            Log.e("LearnScreen", "Error: " + line, e);
            e.printStackTrace();
        }
    }

    /* Check If Number Exists In File */
    public boolean isValid(int i) {
        return (i >= MIN) && (i <= MAX);
    }

    /* Convert Digit (Int) -> English (String) */
    public String getEnglishFromDigit(int num) {
        if (!isValid(num))
            throw new IllegalArgumentException("Invalid Number Requested");

        return numbersEng.get(num);
    }

    /* Convert Digit (Int) -> German (String) */
    public String getGermanFromDigit(int num) {
        if (!isValid(num))
            throw new IllegalArgumentException("Invalid Number Requested");

        return numbersGer.get(num);
    }

    /* Convert Digit (Int) -> Audio File (String) */
    public String getAudioFileFromDigit(int num) {
        if (!isValid(num))
            throw new IllegalArgumentException("Invalid Number Requested");

        return numbersAudioFiles.get(num);
    }

}
