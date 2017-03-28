package com.jackson.guitar;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by jackson on 3/15/2017.
 */
public class GuitarCanvas extends Canvas implements EventHandler<MouseEvent> {


    private ScaleDictionary scaleDictionary;
    private PianoCanvas pianoCanvas;

    public void setPianoCanvas(PianoCanvas pianoCanvas) {
        this.pianoCanvas = pianoCanvas;
    }

    private Image imageGuitar = new Image("guitar-big.GIF");

    private static final int fretboard[][] =
            {{4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3},
                    {9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8},
                    {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1},
                    {7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6},
                    {11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                    {4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3}};

    private List<Integer> xCoords;
    private List<Integer> yCoords;

    private List<Integer> xCoords_big = new ArrayList<>();
    private List<Integer> yCoords_big = new ArrayList<>();

    private ArrayList scale = null;

    private int root = 0;

    private boolean string[] = new boolean[6];

    Rectangle stringRect[] = new Rectangle[6];

    Rectangle noteRect[][] = new Rectangle[6][24];

    static int currentPos = 0;

    int currentNote = 0;
    String currentScale = "Ionian";

    public GuitarCanvas() {

        init();
        scaleDictionary = ScaleDictionary.createDict();

//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScale, currentNote);
        setWidth(imageGuitar.getWidth());
        setHeight(imageGuitar.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        setOnMouseClicked(this);

        updateFretboard(new Note(currentNote));

//        setScaleRoot(0);
//        setScaleType("Ionian");
    }

    private void init() {

        xCoords = new ArrayList<>(Arrays.asList(33, 101, 165, 226, 283, 337, 388, 436, 481,
                524, 565, 603, 639, 673, 705, 735, 764, 791,
                817, 841, 863, 885, 905, 924));

        yCoords = new ArrayList<>(Arrays.asList(100, 83, 66, 49, 32, 15));

        for (Integer i : xCoords) {
            Double x = i * 1.5;
            xCoords_big.add(x.intValue());
        }
        for (Integer i : yCoords) {
            Double x = i * 1.5;
            yCoords_big.add(x.intValue());
        }

        for (int i = 0; i < 6; i++) {
            stringRect[i] = new Rectangle(10, (yCoords_big.get(i) - 14), 20, 20);
            string[i] = true;
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 24; j++) {
                int x1 = xCoords_big.get(j - 1);
                int x2 = xCoords_big.get(j);
                int y1 = yCoords_big.get(i);
                int x8 = x2 - x1;
                noteRect[i][j - 1] = new Rectangle(x1, y1 - 7, x8, 14);
            }
        }

    }

    public void setScaleRoot(int rootNote) {
        currentNote = rootNote;
        GraphicsContext gc = getGraphicsContext2D();
//        scaleFinder.setRoot(rootNote);
//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScale, currentNote);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        updateFretboard(new Note(rootNote));
    }

    public void setScaleType(String scaleType) {
        GraphicsContext gc = getGraphicsContext2D();
        currentScale = scaleType;
//        scaleFinder.setScaleName(scaleType);
//        scale = scaleFinder.getScale();
        scale = (ArrayList) scaleDictionary.getScaleNotesFromNameAndRoot(currentScale, currentNote);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        updateFretboard(new Note(root));
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("X: " + event.getX() + "     Y: " + event.getY());
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        Note note = getNoteFromPosition(event.getX(), event.getY());
        if (note != null) {
            if (note.getStatus() == Note.Status.FOUND) {
                setScaleRoot(note.getNote());
                updateFretboard(note);
                if (pianoCanvas != null) {
                    pianoCanvas.setScaleRoot(note.getNote());
                }
            } else if (note.getStatus() == Note.Status.MUTE_STRING) {
                updateFretboard(new Note(currentPos));
            }
        }
    }

    private Note getNoteFromPosition(double x, double y) {
        for (int i = 0; i < 6; i++) {
            if (stringRect[i].contains(x, y)) {
                string[i] = !string[i];
                Note note = new Note(0);
                note.setStatus(Note.Status.MUTE_STRING);
                return note;
            }
            for (int j = 0; j < 23; j++) {
                Rectangle rect = noteRect[i][j];
                if (rect.contains(x, y)) {
                    return new Note(fretboard[i][j + 1]);
                }
            }
        }

        return null;
    }

    private void updateFretboard( Note rootNote) {
        GraphicsContext g = getGraphicsContext2D();
        int[] notes = new int[scale.size()];
        int[] pos = new int[scale.size()];
        for (int i = 0; i < scale.size(); i++) {
            Note n = (Note) scale.get(i);
            notes[i] = n.getNote();
            pos[i] = n.getPos();
        }
        if (rootNote != null)
            root = rootNote.getNote();

        if (rootNote.getStatus() != Note.Status.MUTE_STRING) {
            currentPos = root;
            System.out.println("root:  " + root);
        }

        Color rootColor = new Color(1.0, 0.0, 0.0, .5);
        g.setFont(new Font("Serif", 12.0));
        Color lightBlue = Color.LIGHTBLUE;
        for (int i = 0; i < 6; i++) { //6 strings
            for (int j = 0; j < 24; j++) { //24 frets
                boolean bPosFretted = false;
                for (int k = 0; k < notes.length; k++) {
                    if (notes[k] == fretboard[i][j]) {
                        currentPos = pos[k];
                        bPosFretted = true;
                    }
                }
                /** position on fretboard is in scale */
//                if (positionFretted(i, j)) {
                if (bPosFretted) {
                    /** not an open string */
                    if (j != 0) {
                        /** Determine color of note*/
                        if (string[i]) {
                            if (fretboard[i][j] == root)
                                g.setFill(Color.RED); //light blue
                            else {
                                g.setFill(lightBlue);
                            }
                        } else {
                            g.setFill(new Color(.5f, .2f, .2f, .5));
                        }
                        /** draw the note (oval) and the note name*/
                        g.fillOval(noteRect[i][j - 1].getX() + 3, noteRect[i][j - 1].getY(),
                                noteRect[i][j - 1].getWidth() - 6, noteRect[i][j - 1].getHeight());
//                            int x1 = coordX[j - 1];
                        int x1 = xCoords_big.get(j - 1);
                        int x2 = xCoords_big.get(j);
                        int y1 = yCoords_big.get(i);
                        String note = ScaleDictionary.NOTE_NAMES[fretboard[i][j]];
                        if (note.length() >= 2)
                            note = note.substring(0, 2);
                        final Text text = new Text(note);
                        double xText = x1 + (x2 - x1) / 2.0 - text.getLayoutBounds().getWidth() / 2.0;
                        g.setFill(Color.BLACK);
                        g.fillText(note, (int) (xText), y1 + 3);
                    }
                    /** an open string */
                    else {
                        int x = xCoords_big.get(0) - 28;
                        int y = yCoords_big.get(i);
                        if (string[i]) {
                            g.fillOval(x, y - 14, 28,28);
                        } else
                            g.fillOval(x, y - 14, 28,28);
                    }
                }
            }// end for: frets
        }// end for: string

    }

}
