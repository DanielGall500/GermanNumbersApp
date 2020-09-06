package com.example.germanmemoriserapp.mechanics;

import android.content.Context;
import android.util.Log;

import com.example.germanmemoriserapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ScoreBoardManager {

    private final String csvSep = ",";
    private Context appContext;

    private final int best_scores_file_id = R.raw.txt_file_best_scores;
    private final int recent_scores_file_id = R.raw.txt_file_recent_scores;

    private final int EMPTY_SCORE = -1;
    /*
    What Each Column Represents In Recent Scores Txt:
     */
    public static final int difficulty_token_indx = 0;
    public static final int score_token_indx = 1;

    public static final int beginner_token_indx = 0;
    public static final int normal_token_indx = 1;
    public static final int master_token_indx = 2;

    private final String defaultRecentDifficulty = "-1";
    private final String defaultRecentScore = "-1";

    String bestScoresFileName = "txt_file_best_scores.txt";
    String recentScoresFileName = "txt_file_recent_scores.txt";

    private final String LINE_SEP = System.getProperty("line.separator");

    public ScoreBoardManager(Context context) {
        this.appContext = context;
    }

    public ArrayList<ArrayList<String>> getBestScores() {
        return readIn(bestScoresFileName);
    }

    public ArrayList<ArrayList<String>> getRecentScores() {
        return readIn(recentScoresFileName);
    }

    public ArrayList<ArrayList<String>> readIn(String file) {

        ArrayList<ArrayList<String>> scores = new ArrayList<>();
        String line = "";

        FileInputStream fis = null;
        InputStreamReader iStreamReader;
        BufferedReader reader = null;

        try {

            try {
                fis = appContext.openFileInput(file);
            }
            catch(FileNotFoundException e) {
                initTextFiles();

                fis = appContext.openFileInput(file);
            }

            iStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(iStreamReader);

            while((line = reader.readLine()) != null) {

                /* Split into tokens */
                String[] tokens = line.split(csvSep);
                System.out.println(tokens[difficulty_token_indx]);
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
                String diffStr = String.valueOf(diff.getId());
                String scoreStr = scoreToken;

                /*
                Add To Our Array
                 */
                newEntry.add(diffStr);
                newEntry.add(scoreStr);

                scores.add(newEntry);
            }

        }
        catch(IOException e) {
            System.out.println("IO EXCEPTION");
            e.printStackTrace();
        }
        finally {
            if(reader != null) {

                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        System.out.println("Finished");

        return scores;
    }

    private void initTextFiles() {
        initFile(best_scores_file_id, bestScoresFileName);
        initFile(recent_scores_file_id, recentScoresFileName);
    }

    private void initFile(int fromFileId, String toNewFile) {
        /*
        Create Text Files On Initial Download
         */

        InputStream iStream =
                appContext.getResources().openRawResource(
                        fromFileId);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(iStream, Charset.forName("UTF-8")));

        String line = "";

        StringBuilder sb = new StringBuilder();

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(LINE_SEP);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        updateFile(sb.toString(), toNewFile);
    }

    public void update(int difficultyId, int score) {

        //Smaller Number Of Seconds <=> Better Score
        if(isNewBestScore(difficultyId, score)) {
            Difficulty diff = new Difficulty(difficultyId);
            editBestScore(diff, score);
        }

        /*
        Update Recent Scores
         */
        addRecentScore(difficultyId, score);
    }

    private boolean isNewBestScore(int difficultyId, int score) {
        int topScore = getTopScore(difficultyId);
        boolean isLessSeconds = score < topScore;

        return isLessSeconds || isEmptyScore(topScore);
    }

    private boolean isEmptyScore(int score) {
        return score == EMPTY_SCORE;
    }

    public int getTopScore(int difficultyId) {
        ArrayList<ArrayList<String>> bestScores = getBestScores();

        ArrayList<String> scoreSet;

        Difficulty d = new Difficulty(difficultyId);

        switch(d.getDifficulty()) {
            case BEGINNER:
                scoreSet = bestScores.get(beginner_token_indx);
                System.out.println(scoreSet.get(1));
                break;
            case NORMAL:
                scoreSet = bestScores.get(normal_token_indx);
                System.out.println(scoreSet.get(1));
                break;
            case MASTER:
                scoreSet = bestScores.get(master_token_indx);
                System.out.println(scoreSet.get(1));
                break;
            default:
                throw new IllegalArgumentException("Invalid Difficulty");
        }

        return Integer.parseInt(scoreSet.get(score_token_indx));
    }

    public void editBestScore(Difficulty d, int score) {
        String scoreStr = String.valueOf(score);

        ArrayList<String> bestScore;

        ArrayList<ArrayList<String>> bestScores = getBestScores();

        switch (d.getDifficulty()) {
            case BEGINNER:
                bestScore = bestScores.get(beginner_token_indx);
                break;
            case NORMAL:
                bestScore = bestScores.get(normal_token_indx);
                break;
            case MASTER:
                bestScore = bestScores.get(master_token_indx);
                break;
            default:
                throw new IllegalArgumentException("Invalid Difficulty");
        }

        System.out.println(bestScores.toArray().toString());

        bestScore.set(score_token_indx, scoreStr);

        System.out.println(bestScores.toArray().toString());

        updateFile(bestScores, bestScoresFileName);
    }

    public void addRecentScore(int difficultyId, int score) {
        Difficulty diff = new Difficulty(difficultyId);
        String diffIdStr = String.valueOf(diff.getId());
        String scoreStr = String.valueOf(score);

        ArrayList<ArrayList<String>> recentScores = getRecentScores();

        ArrayList<String> newScore = new ArrayList<>();
        newScore.add(difficulty_token_indx, diffIdStr);
        newScore.add(score_token_indx, scoreStr);

        int N = recentScores.size();

        recentScores.remove(N-1);
        recentScores.add(0, newScore);

        updateFile(recentScores, recentScoresFileName);
    }

    public String listToCSV(ArrayList<ArrayList<String>> list) {
        StringBuilder sb = new StringBuilder();

        int N = list.size();

        for(int i = 0; i < N; i++) {
            ArrayList<String> next = list.get(i);

            String difficulty = next.get(difficulty_token_indx);
            String score = next.get(score_token_indx);

            String line = String.format("%s,%s", difficulty,score);

            sb.append(line);
            sb.append(LINE_SEP);
        }

        return sb.toString();
    }

    private void updateFile(String updatedFile, String file) {
        FileOutputStream fos = null;

        try {
            fos = appContext.openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(updatedFile.getBytes());
        }
        catch(FileNotFoundException e) {
            Log.e("FileNotFound Exception", "File Write Failed: " + e.toString());
        }
        catch(IOException e) {
            Log.e("Exception", "File Write Failed: " + e.toString());
        }
        finally {
            if(fos != null) {
                try {
                    fos.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateFile(ArrayList<ArrayList<String>> bestScores, String file) {
        String updatedFile = listToCSV(bestScores);
        updateFile(updatedFile, file);
    }

    private String createScoreString(String score) {
        return String.format("%s Seconds", score);
    }
}
