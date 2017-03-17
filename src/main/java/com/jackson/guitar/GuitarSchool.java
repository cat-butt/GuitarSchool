package com.jackson.guitar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuAbout);

        GuitarCanvas guitarCanvase = new GuitarCanvas();

        PianoCanvas pianoCanvas = new PianoCanvas();

        Group root = new Group();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, guitarCanvase, pianoCanvas);
        root.getChildren().addAll(vBox);

        Scene scene = new Scene(root);

        primaryStage.setTitle("GuitarSchool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
