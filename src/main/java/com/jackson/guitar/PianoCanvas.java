package com.jackson.guitar;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jackson on 3/17/2017.
 */
public class PianoCanvas extends Canvas {

    private GuitarCanvas guitarCanvas;

    public void setGuitarCanvas(GuitarCanvas guitarCanvas) {
        this.guitarCanvas = guitarCanvas;
    }

    private static Image imagePiano = new Image("piano_big.GIF");

//    private ScaleFinder scaleFinder = new ScaleFinder();
    private ScaleDictionary scaleDictionary;

    private ArrayList scale;

    private List<Integer> keyboard = new ArrayList<>(Arrays.asList(8, 20, 29, 41, 50, 71, 83, 92, 104, 113, 125, 134));
    private List<Integer> keyboard_big = new ArrayList<>();

    private String currentScaleName = "Ionian";
    private int currentRoot = 0;

    public PianoCanvas() {

        for (Integer i : keyboard) {
            keyboard_big.add(i * 2);
        }

        scaleDictionary = ScaleDictionary.createDict();

//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScaleName, currentRoot);
        setWidth(imagePiano.getWidth());
        setHeight(imagePiano.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        setScaleRoot(0);
        setScaleType("Ionian");
    }

    public void setScaleRoot(int rootNote) {
        currentRoot = rootNote;
        GraphicsContext gc = getGraphicsContext2D();
//        scaleFinder.setRoot(rootNote);
//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScaleName, currentRoot);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        updateKeyboard(new Note(rootNote));
    }

    public void setScaleType(String scaleType) {
        GraphicsContext gc = getGraphicsContext2D();
        currentScaleName = scaleType;
//        scaleFinder.setScaleName(scaleType);
//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScaleName, currentRoot);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        updateKeyboard(new Note(currentRoot));
    }

    private void updateKeyboard(Note rootNote) {
        GraphicsContext g = getGraphicsContext2D();
        int[] notes;
        notes = new int[scale.size()];
        for (int i = 0; i < scale.size(); i++) {
            Note n = (Note) scale.get(i);
            notes[i] = n.getNote();
        }

        HashSet set = new HashSet(scale);
        for (int i = 0; i < 12; i++) {
            boolean bNoteInScale = false;
            for (int k = 0; k < notes.length; k++) {
                if (notes[k] == i) {
                    bNoteInScale = true;
                    break;
                }
            }
            if (bNoteInScale) {
                if (i == currentRoot)
                    g.setFill(Color.RED);
                else
                    g.setFill(Color.LIGHTBLUE);
                if (i != 1 && i != 3 && i != 6 && i != 8 && i != 10)
                    for (int j = 0; j < 3; j++)
                        g.fillOval(keyboard_big.get(i) + j * 294, 50, 20, 20);
                else
                    for (int j = 0; j < 3; j++)
                        g.fillOval(keyboard_big.get(i) + j * 294, 16, 20, 20);
            }
        }
    }
}
