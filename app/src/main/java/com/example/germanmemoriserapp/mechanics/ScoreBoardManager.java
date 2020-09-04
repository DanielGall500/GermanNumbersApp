package com.example.germanmemoriserapp.mechanics;

import android.content.Context;
import android.util.Log;

import com.example.germanmemoriserapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ScoreBoardManager {

    private final int NUM_RECENT_SCORES = 10;

    private final String csvSep = ",";
    private Context appContext;

    private final int best_scores_file_id = R.raw.txt_file_best_scores;
    private final int recent_scores_file_id = R.raw.txt_file_recent_scores;

    private InputStream iStream;
    private BufferedReader reader;

    /*
    What Each Column Represents In Recent Scores Txt:
     */
    private int difficulty_token_indx = 0;
    private int score_token_indx = 1;

    private final String[] defaultBestScores = new String[] {
            "None Yet!",
            "None Yet!",
            "None Yet!"

    };

    private final String defaultRecentDifficulty = "No Difficulty";
    private final String defaultRecentScore = "None";

    public ScoreBoardManager(Context context) {
        this.appContext = context;
    }

    public ArrayList<ArrayList<String>> getBestScores() {
        iStream = appContext.getResources().openRawResource(
                best_scores_id);

        reader = new BufferedReader(
                new InputStreamReader(iStream, Charset.forName("UTF-8")));

        ArrayList<ArrayList<String>> bestScores = new ArrayList<>();

        String bestScores;
        String[] scoreTokens;

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {

                /* Split into tokens */
                String[] tokens = line.split(csvSep);
                int diffId = Integer.parseInt(tokens[difficulty_token_indx]);
                String scoreToken = tokens[score_token_indx];

                ArrayList<String> newEntry = new ArrayList<>();


                bestScores.add(newEntry);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            return defaultBestScores;
        }

    }

    public ArrayList<ArrayList<String>> getBestScores() {
        return readIn(best_scores_file_id);
    }

    public ArrayList<ArrayList<String>> getRecentScores() {
        return readIn(recent_scores_file_id);
    }

    public ArrayList<ArrayList<String>> readIn(int resourceId) {
        iStream = appContext.getResources().openRawResource(
                resourceId);

        reader = new BufferedReader(
                new InputStreamReader(iStream, Charset.forName("UTF-8")));

        String line = "";

        ArrayList<ArrayList<String>> scores = new ArrayList<>();

        try {
            while((line = reader.readLine()) != null) {

                /* Split into tokens */
                String[] tokens = line.split(csvSep);
                int diffId = Integer.parseInt(tokens[difficulty_token_indx]);
                String scoreToken = tokens[score_token_indx];

                ArrayList<String> newEntry = new ArrayList<>();

                /* If Not Enough Game Have Been Played Yet */
                if(!Difficulty.isValid(diffId)) {
                    newEntry.add(defaultRecentDifficulty);
                    newEntry.add(defaultRecentScore);

                    scores.add(newEntry);
                    continue;
                }

                /*
                Create Difficulty Object
                 */
                Difficulty diff = new Difficulty(diffId);

                /*
                Retrieve Difficulty & Score In String Form
                 */
                String diffStr = diff.toString();
                String scoreStr = createScoreString(scoreToken);

                /*
                Add To Our Array
                 */
                newEntry.add(diffStr);
                newEntry.add(scoreStr);

                scores.add(newEntry);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

    private String createScoreString(String score) {
        return String.format("%s Seconds", score);
    }


}
