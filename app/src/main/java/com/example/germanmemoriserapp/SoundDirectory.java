package com.example.germanmemoriserapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SoundDirectory {

    private String FOLDER = "raw";
    private String FILE_PREFIX = "number_";

    private HashMap<Integer, Integer> dirMap;

    public SoundDirectory() {
        this.dirMap = new HashMap<>();
    }

    public Queue<Integer> getResIds(ArrayList<Integer> nums, Context context) {
        Queue<Integer> ids = new LinkedList<>();
        int N = nums.size();

        for(int i = 0; i < N; i++) {
            int num = nums.get(i);

            String file = getFileStr(num);
            int id = getId(file, context);

            System.out.println("Adding new id: " + id);

            dirMap.put(id, num);

            ids.offer(id);
        }

        System.out.println("PRESIZE: " + dirMap.keySet().size());

        return ids;
    }

    public int getNum(int id) {
        System.out.println("ID: " + id);
        System.out.println(dirMap.containsKey(id));

        for(int i : dirMap.keySet())
            System.out.print(i + ", ");

        System.out.println("SIZE: " + dirMap.keySet().size());

        if(!dirMap.containsKey(id)) {
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



}
