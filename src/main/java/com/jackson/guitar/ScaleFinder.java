package com.jackson.guitar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Created by jackson on 3/16/2017.
 */
public class ScaleFinder {
    public static final int NOTE_MAPPING[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    public static final String NOTE_MAPPING_RELATIVE_NAMES[] = {"b9", "9", "m3", "M3", "4", "b5", "5", "#5", "6", "b7", "maj7"};
    public static final String NOTE_NAMES[] = {"C", "C#/Db", "D", "Eb/D#", "E", "F", "F#/Gb", "G", "Ab/G#", "A", "Bb/A#", "B"};
    private static HashMap masterList = new HashMap();
    private int currentRoot = 0;
    private String currentScaleName = "Ionian";
    private int[] currentScale;
    int index = 0;

    private ScaleDictionary scaleDictionary = null;

    ScaleFinder() {
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] decodeValue(String s) {
        int[] array = new int[s.length()];
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = Character.digit(s.charAt(i), 10);
            sum += array[i];
        }
        System.out.println(s + ": " + sum);
        return array;
    }

    public ArrayList<String> getGroupNameList() {
        return scaleDictionary.getFamilyList();
    }

    public ArrayList<String> getGroup(String group) {
        return scaleDictionary.getScaleListUnderFamily(group);
    }

    public void setRoot(int root) {
        this.currentRoot = root;
    }

    public void setRoot(String root) {
        for (int i = 0; i < NOTE_NAMES.length; i++) {
            if (root.equals(NOTE_NAMES[i]))
                currentRoot = i;
        }
    }

    public void setScaleName(String type) {
        this.currentScaleName = type;
    }

    public ArrayList<Note> getScale() {
        ArrayList<Integer> stepArray = scaleDictionary.getScale(currentScaleName);
        int current = currentRoot;
        ArrayList<Note> list = new ArrayList<>();
        for( int i = 0; i < stepArray.size(); i++) {
            list.add(new Note(current % 12, i + 1));
            current += stepArray.get(i);
        }
        return list;
    }

    public int getRoot() {
        return currentRoot;
    }


}
