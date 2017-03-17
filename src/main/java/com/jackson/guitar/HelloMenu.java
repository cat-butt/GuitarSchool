package com.jackson.guitar;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by jackson on 3/15/2017.
 */
public class HelloMenu extends Application {

    @Override
    public void start(Stage stage) {

        Image imageGuitar = new Image("guitar.GIF");
        ImageView imageViewGuitar = new ImageView();
        imageViewGuitar.setImage(imageGuitar);

        Image imagePiano = new Image("piano.GIF");
        ImageView imageViewPiano = new ImageView();
        imageViewPiano.setImage(imagePiano);

        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
//        ImageView iv2 = new ImageView();
//        iv2.setImage(image);
//        iv2.setFitWidth(100);
//        iv2.setPreserveRatio(true);
//        iv2.setSmooth(true);
//        iv2.setCache(true);
//
//         defines a viewport into the source image (achieving a "zoom" effect) and
//         displays it rotated
//        ImageView iv3 = new ImageView();
//        iv3.setImage(image);
//        Rectangle2D viewportRect = new Rectangle2D(40, 35, 110, 110);
//        iv3.setViewport(viewportRect);
//        iv3.setRotate(90);

        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        HBox box = new HBox();
        box.getChildren().add(imageViewPiano);
        box.getChildren().add(imageViewGuitar);
        root.getChildren().add(box);

        stage.setTitle("ImageView");
        stage.setWidth(415);
        stage.setHeight(200);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
