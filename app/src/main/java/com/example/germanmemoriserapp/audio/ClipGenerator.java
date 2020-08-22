package com.example.germanmemoriserapp.audio;

import java.util.ArrayList;

public class ClipGenerator {

    NumberGenerator gen;

    public ClipGenerator() {

        gen = new NumberGenerator();

        ArrayList<Integer> generatedArr = gen.generateArray(1, 50, 5);



    }
}
