package com.jackson.guitar;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jackson on 3/17/2017.
 */
public class PianoCanvas extends Canvas implements EventHandler<MouseEvent> {

    private GuitarCanvas guitarCanvas;

    public void setGuitarCanvas(GuitarCanvas guitarCanvas) {
        this.guitarCanvas = guitarCanvas;
    }

    private static Image imagePiano = new Image("piano.GIF");

    private ScaleFinder scaleFinder = new ScaleFinder();

    private ArrayList scale;

    private int root;

    public PianoCanvas() {
        scale = scaleFinder.getScale();
        setScaleRoot(0);
        setScaleType("Ionian");
        setWidth(imagePiano.getWidth());
        setHeight(imagePiano.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        setOnMouseClicked(this);
    }

    public void setScaleRoot(int rootNote) {
        GraphicsContext gc = getGraphicsContext2D();
        scaleFinder.setRoot(rootNote);
        scale = scaleFinder.getScale();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        new PianoMapper(getGraphicsContext2D(), new Note(rootNote));
    }

    public void setScaleType(String scaleType) {
        GraphicsContext gc = getGraphicsContext2D();
        scaleFinder.setScaleName(scaleType);
        scale = scaleFinder.getScale();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        new PianoMapper(getGraphicsContext2D(), new Note(root));
    }
    @Override
    public void handle(MouseEvent event) {
        System.out.println("Piano:    x: " + event.getX() + "   y: " + event.getY());
    }

    class PianoMapper {
        private int[] notes;
        {
            notes = new int[scale.size()];
            for(int i=0; i<scale.size(); i++) {
                Note n = (Note)scale.get(i);
                notes[i] = n.getNote();
            }
        }
        private final int KEYBOARD[] = { 8,20,29,41,50,71,83,92,104,113,125,134 };
        PianoMapper(GraphicsContext g, Note rootNote) {
            HashSet set = new HashSet(scale);
            for(int i=0; i<12; i++) {
                if(noteInScale(i)) {
                    if(i == root)
                        g.setFill(Color.BLUE);
                    else
                        g.setFill(new Color(0.6F,0.6F,0.0F, .5));
                    if(i!=1 && i!=3 && i!=6 && i!=8 && i!=10)
                        for(int j=0; j<3; j++)
                            g.fillOval(KEYBOARD[i]+j*147,25,10,10);
                    else
                        for(int j=0; j<3; j++)
                            g.fillOval(KEYBOARD[i]+j*147,8,10,10);
                }
            }
        }
        private boolean noteInScale(int n) {
            for(int k=0; k<notes.length; k++) {
                if(notes[k] == n)
                    return true;
            }
            return false;
        }
    }

}
