package com.jackson.guitar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by jackson on 3/15/2017.
 */
public class GuitarSchool extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

//        final Image imageGuitar = new Image("guitar.GIF");
//        final Image imagePiano = new Image("piano.GIF");
//
//        Canvas guitarCanvas = new Canvas(imageGuitar.getWidth(), imageGuitar.getHeight());
//        final GraphicsContext gc = guitarCanvas.getGraphicsContext2D();
//        gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
//        gc.fillOval(100, 100, 50, 50);
//        guitarCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println(event.getX() + "        " + event.getY());
//                gc.drawImage(imageGuitar, 0, 0, imageGuitar.getWidth(), imageGuitar.getHeight());
//                gc.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
//            }
//        });
//
//        Canvas pianoCanvas = new Canvas(imagePiano.getWidth(), imagePiano.getHeight());
//        final GraphicsContext gc2 = pianoCanvas.getGraphicsContext2D();
//        gc2.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
//        gc2.fillOval(100, 100, 50, 50);
//        pianoCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println(event.getX() + "        " + event.getY());
//                gc2.drawImage(imagePiano, 0, 0, imagePiano.getWidth(), imagePiano.getHeight());
//                gc2.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
//            }
//        });

        GuitarCanvas gCanvase = new GuitarCanvas();

        Group root = new Group();
        VBox vBox = new VBox();
//        vBox.getChildren().addAll(guitarCanvas, pianoCanvas, gCanvase);
        vBox.getChildren().addAll(gCanvase);
        root.getChildren().add(vBox);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Hello...");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
