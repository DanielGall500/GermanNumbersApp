package com.example.germanmemoriserapp.audio;

import android.content.Context;

import com.example.germanmemoriserapp.mechanics.Difficulty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SoundDirectory {

    private final String FOLDER = "raw";
    private final String FILE_PREFIX = "number_";

    private ArrayList<Integer> numberArray = new ArrayList<>();

    private HashMap<Integer, Integer> dirMap;

    public SoundDirectory() {
        this.dirMap = new HashMap<>();
    }

    protected Queue<Integer> generateIds(Difficulty diff, Context context, int N) {
        int min = diff.getMin();
        int max = diff.getMax();

        NumberGenerator generator = new NumberGenerator();

        Queue<Integer> ids = new LinkedList<>();
        numberArray = new ArrayList<>();

        for (int i = 0; i < N; i++) {

            int nxt = generator.generateNumber(min, max);

            if (hasID(nxt, context) && !numberArray.contains(nxt)) {
                /*
                Find ID associated with the number.
                 */
                int id = getId(getFileStr(nxt), context);

                /*
                Map ID => number.
                 */
                dirMap.put(id, nxt);

                /*
                Add ID to QUEUE.
                 */
                ids.offer(id);

                /*
                Add number to array.
                 */
                numberArray.add(nxt);

            } else {
                /*
                If there is no sound file associated with
                this number, we repeat this step.
                 */

                i--;
            }
        }

        return ids;
    }

    protected ArrayList<Integer> getIntegerArray() {
        return this.numberArray;
    }

    protected int getNum(int id) {
        if (!dirMap.containsKey(id)) {
            throw new IllegalArgumentException("Invalid ID");
        } else {
            return dirMap.get(id);
        }
    }

    private String getFileStr(int n) {
        return String.format("%s%d", FILE_PREFIX, n);
    }

    private int getId(String fileStr, Context context) {
        int resId = context.getResources().getIdentifier(
                fileStr, FOLDER, context.getPackageName()
        );

        return resId;
    }

    protected boolean hasID(int num, Context context) {
        /*
        getIdentifier method returns 0 if a resource cannot
        be found. Thus we can ensure there is a valid id
        by ensuring 0 is not returned.
         */
        return context.getResources().getIdentifier(
                getFileStr(num), FOLDER, context.getPackageName()) != 0;
    }

    private class NumberGenerator {

        private Random randGen;

        public NumberGenerator() {
            randGen = new Random();
        }

        private ArrayList<Integer> generateArray(int min, int max, int size) {
            ArrayList<Integer> generated = new ArrayList<>();

            int tmp;
            for (int i = 0; i < size; i++) {
                tmp = generateNumber(min, max);
                generated.add(tmp);
            }

            return generated;
        }

        private int generateNumber(int min, int max) {
            return randGen.nextInt(max - min + 1) + min;
        }
    }
}
