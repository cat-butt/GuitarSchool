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


    private PianoCanvas pianoCanvas;

    public void setPianoCanvas(PianoCanvas pianoCanvas) {
        this.pianoCanvas = pianoCanvas;
    }

    private Image imageGuitar = new Image("guitar-big.GIF");

    private ScaleFinder scaleFinder = new ScaleFinder();

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


    public GuitarCanvas() {

        init();

        scale = scaleFinder.getScale();
        setWidth(imageGuitar.getWidth());
        setHeight(imageGuitar.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        setOnMouseClicked(this);

        setScaleRoot(0);
        setScaleType("Ionian");
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
        GraphicsContext gc = getGraphicsContext2D();
        scaleFinder.setRoot(rootNote);
        scale = scaleFinder.getScale();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        new FretboardMapper(getGraphicsContext2D(), new Note(rootNote));
    }

    public void setScaleType(String scaleType) {
        GraphicsContext gc = getGraphicsContext2D();
        scaleFinder.setScaleName(scaleType);
        scale = scaleFinder.getScale();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
//        gc.setFill(Color.AQUA);
//        for( Rectangle rect: stringRect ) {
//            gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
//        }
        new FretboardMapper(getGraphicsContext2D(), new Note(root));
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
                new FretboardMapper(getGraphicsContext2D(), note);
                if (pianoCanvas != null) {
                    pianoCanvas.setScaleRoot(note.getNote());
                }
            } else if (note.getStatus() == Note.Status.MUTE_STRING) {
                new FretboardMapper(getGraphicsContext2D());

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
//                System.out.println(rect);
//                if( noteRect[i][j].contains(x,y)) {
                if (rect.contains(x, y)) {
                    return new Note(fretboard[i][j + 1]);
                }
            }
        }

        return null;
    }

    class FretboardMapper {
        private int[] notes;
        /**
         * position of the note relative to the scale
         */
        private int[] pos;


        FretboardMapper(GraphicsContext g) {
            this(g, new Note(currentPos));
        }

        FretboardMapper(GraphicsContext g, Note rootNote) {
            {
                notes = new int[scale.size()];
                pos = new int[scale.size()];
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
            }

            Color rootColor = new Color(1.0, 0.0, 0.0, .5);
//            Color rootColor = new Color(1.0f,0.0f,0.0f);
            g.setFont(new Font("Serif", 12.0));
//            g.setFont(new Font("Serif",Font.PLAIN,10));
//            FontMetrics fm = getFontMetrics(g.getFont());
            Color lightBlue = Color.LIGHTBLUE;
            for (int i = 0; i < 6; i++) { //6 strings
                for (int j = 0; j < 24; j++) { //24 frets
                    /** position on fretboard is in scale */
                    if (positionFretted(i, j)) {
                        /** not an open string */
                        if (j != 0) {
                            /** Determine color of note*/
                            if (string[i]) {
                                if (fretboard[i][j] == root)
                                    g.setFill(Color.RED); //light blue
//                                g.setColor(rootColor); //light blue
                                else {
                                    g.setFill(lightBlue);
//                                    float num = (float) pos.length;
//                                    float red = 1.0f - (float) currentPos / num;
//                                    float green = 1.0f - (1.0f - (float) currentPos / num) / 2.0f;
//                                    float blue = (float) currentPos / num;
//                                    g.setFill(new Color(red, green, blue, .9));
                                }
                            } else {
                                g.setFill(new Color(.5f, .2f, .2f, .5));
//                            g.setColor(Color.gray);
                            }
                            /** draw the note (oval) and the note name*/
                            g.fillOval(noteRect[i][j - 1].getX() + 3, noteRect[i][j - 1].getY(),
                                    noteRect[i][j - 1].getWidth() - 6, noteRect[i][j - 1].getHeight());
//                            int x1 = coordX[j - 1];
                            int x1 = xCoords_big.get(j - 1);
                            int x2 = xCoords_big.get(j);
                            int y1 = yCoords_big.get(i);
                            String note = ScaleFinder.NOTE_NAMES[fretboard[i][j]];
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
//                                if(fretboard[i][j] == root)
//                                    g.setColor(rootColor);
//                                else
//                                    g.setColor(Color.yellow);
                            } else
//                                g.setColor(new Color(.5f,.2f,.2f));
                                g.fillOval(x, y - 14, 28,28);
                        }
                    }
                }// end for: frets
            }// end for: string
        }// end FretboardMapper constructor

        private boolean positionFretted(int i, int j) {
            for (int k = 0; k < notes.length; k++) {
                if (notes[k] == fretboard[i][j]) {
                    currentPos = pos[k];
                    return true;
                }
            }
            return false;
        }
    }

}
