package ucd.danielgall.klangapp.utilities;

import ucd.danielgall.klangapp.sound.elements.SoundElement;
import ucd.danielgall.klangapp.sound.elements.NumberClip;

import java.util.ArrayList;
import java.util.Random;

public class NumberGenerator {

    private Random randGen = new Random();

    public ArrayList<Integer> generateRandom(int min, int max, int N) {

        ArrayList<Integer> generated = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int tmp = generateNumber(min, max);
            if (!generated.contains(tmp)) {
                generated.add(tmp);
            } else {
                i--;
            }
        }

        return generated;
    }

    public ArrayList<SoundElement> generateNormal(int min, int max) {
        ArrayList<SoundElement> arr = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            arr.add(new NumberClip(i));
        }

        return arr;
    }

    private int generateNumber(int min, int max) {
        return randGen.nextInt(max - min + 1) + min;
    }
}
