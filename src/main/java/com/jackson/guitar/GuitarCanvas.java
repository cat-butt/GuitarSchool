package com.jackson.guitar;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by jackson on 3/15/2017.
 */
public class GuitarCanvas extends Canvas implements EventHandler<MouseEvent> {

    private Image imageGuitar = new Image("guitar.GIF");

    private final int FRETBOARD[][] =
            {{4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3},
                    {9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8},
                    {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1},
                    {7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6},
                    {11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                    {4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3}};

    private final int coordX[] = {33, 101, 165, 226, 283, 337, 388, 436, 481,
            524, 565, 603, 639, 673, 705, 735, 764, 791,
            817, 841, 863, 885, 905, 924};

    private final int coordY[] = {100, 83, 66, 49, 32, 15};

    private ArrayList scale = null;

    private int root = 0;

    private boolean string[] = new boolean[6];

    Rectangle stringRect[] = new Rectangle[6];

    {
        for (int i = 0; i < 6; i++) {
            stringRect[i] = new Rectangle(5, coordY[i] - 7, 10, 10);
            string[i] = true;
        }
    }

    Rectangle noteRect[][] = new Rectangle[6][24];

    {
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 24; j++) {
                int x1 = coordX[j - 1];
                int x2 = coordX[j];
                int y1 = coordY[i];
                int x8 = (x2 - x1);
                noteRect[i][j - 1] = new Rectangle(x1, y1 - 7, x8, 14);
            }
        }
    }

    public GuitarCanvas() {
        setScale(0);
        setWidth(imageGuitar.getWidth());
        setHeight(imageGuitar.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        setOnMouseClicked(this);
    }

    public void setScale(int rootNote) {
        ScaleFinder scaleFinder = new ScaleFinder();
        scaleFinder.setRoot(rootNote);
        scale = scaleFinder.getScale();
        root = 0;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("X: " + event.getX() + "     Y: " + event.getY());
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
        Note note = getNoteFromPosition(event.getX(), event.getY());
        if (note != null) {
            setScale(note.getNote());
            new FretboardMapper(getGraphicsContext2D(), note);
        }
    }

    private Note getNoteFromPosition(double x, double y) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 23; j++) {
                Rectangle rect = noteRect[i][j];
//                System.out.println(rect);
//                if( noteRect[i][j].contains(x,y)) {
                if (rect.contains(x, y)) {
                    return new Note(FRETBOARD[i][j + 1]);
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

        int currentPos = 0;

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

                currentPos = root;
                System.out.println("root:  " + root);
            }

            Color rootColor = new Color(1.0, 0.0, 0.0, .5);
//            Color rootColor = new Color(1.0f,0.0f,0.0f);
            g.setFont(new Font("Serif", 10.0));
//            g.setFont(new Font("Serif",Font.PLAIN,10));
//            FontMetrics fm = getFontMetrics(g.getFont());
            for (int i = 0; i < 6; i++) { //6 strings
                for (int j = 0; j < 24; j++) { //24 frets
                    /** position on fretboard is in scale */
                    if (positionFretted(i, j)) {
                        /** not an open string */
                        if (j != 0) {
                            /** Determine color of note*/
                            if (string[i]) {
                                if (FRETBOARD[i][j] == root)
                                    g.setFill(Color.RED); //light blue
//                                g.setColor(rootColor); //light blue
                                else {
                                    g.setFill(Color.CHARTREUSE);
                                    float num = (float) pos.length;
                                    float red = 1.0f - (float) currentPos / num;
                                    float green = 1.0f - (1.0f - (float) currentPos / num) / 2.0f;
                                    float blue = (float) currentPos / num;
                                    g.setFill(new Color(red, green, blue, .5));
                                }
                            } else {
                                g.setFill(new Color(.5f, .2f, .2f, .5));
//                            g.setColor(Color.gray);
                            }
                            /** draw the note (oval) and the note name*/
                            g.fillOval(noteRect[i][j - 1].getX() + 3, noteRect[i][j - 1].getY(),
                                    noteRect[i][j - 1].getWidth() - 6, noteRect[i][j - 1].getHeight());
                            int x1 = coordX[j - 1];
                            int x2 = coordX[j];
                            int y1 = coordY[i];
                            String note = ScaleFinder.NOTE_NAMES[FRETBOARD[i][j]];
                            if (note.length() >= 2)
                                note = note.substring(0, 2);
//                            double xText = x1+(x2-x1)/2.0-fm.stringWidth(note)/2.0;
//                            g.setColor(Color.black);
//                            g.drawString(note,(int)(xText),y1+3);
                        }
                        /** an open string */
                        else {
                            int x = coordX[0] - 14;
                            int y = coordY[i];
                            if (string[i]) {
//                                if(FRETBOARD[i][j] == root)
//                                    g.setColor(rootColor);
//                                else
//                                    g.setColor(Color.yellow);
                            } else
//                                g.setColor(new Color(.5f,.2f,.2f));
                                g.fillOval(x, y - 7, 14, 14);
                        }
                    }
                }// end for: frets
            }// end for: string
        }// end FretboardMapper constructor

        private boolean positionFretted(int i, int j) {
            for (int k = 0; k < notes.length; k++) {
                if (notes[k] == FRETBOARD[i][j]) {
                    currentPos = pos[k];
                    return true;
                }
            }
            return false;
        }
    }

}
