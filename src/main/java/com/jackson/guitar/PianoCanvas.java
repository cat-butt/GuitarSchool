package com.jackson.guitar;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Created by jackson on 3/17/2017.
 */
public class PianoCanvas extends Canvas implements EventHandler<MouseEvent> {
    private static Image imagePiano = new Image("piano.GIF");

    private ArrayList scale;
    private int root;
    public PianoCanvas() {
        setWidth(imagePiano.getWidth());
        setHeight(imagePiano.getHeight());
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
        setOnMouseClicked(this);
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Piano:    x: " + event.getX() + "   y: " + event.getY());
    }
}
