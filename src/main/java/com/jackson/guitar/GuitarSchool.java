package com.jackson.guitar;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by jackson on 3/15/2017.
 */
public class GuitarSchool extends Application {

    private GuitarCanvas guitarCanvas;
    private PianoCanvas pianoCanvas;

    @Override
    public void start(Stage primaryStage) throws Exception {

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuAbout);

        BorderPane border = new BorderPane();

        border.setBottom(addGridPane());

        guitarCanvas = new GuitarCanvas();
        pianoCanvas = new PianoCanvas();

        guitarCanvas.setPianoCanvas(pianoCanvas);
        pianoCanvas.setGuitarCanvas(guitarCanvas);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, guitarCanvas, pianoCanvas);
        border.setCenter(vBox);

        Scene scene = new Scene(border);

        primaryStage.setTitle("GuitarSchool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane addGridPane() {
        final ToggleGroup toggleGroup = new ToggleGroup();
        ArrayList<VBox> vBoxes = new ArrayList<>();
        ScaleFinder scaleFinder = new ScaleFinder();
        ArrayList<String> familyList = scaleFinder.getGroupNameList();

        GridPane gridPane = new GridPane();

        int nCount = 0;
        for( String familyName: familyList ) {
            VBox vbox = new VBox();
            ArrayList<RadioButton> radioButtons = new ArrayList<>();
            vbox.getChildren().add(new Label(familyName));
            for( String s: scaleFinder.getGroup(familyName) ) {
                RadioButton radioButton = new RadioButton(s);
                radioButton.setToggleGroup(toggleGroup);
                radioButtons.add(radioButton);
            }
            vbox.getChildren().addAll(radioButtons);
           gridPane.add(vbox,nCount++, 1);
        }

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if( toggleGroup.getSelectedToggle() != null) {
                    guitarCanvas.setScaleType(((Labeled)newValue).getText());
                    pianoCanvas.setScaleType(((Labeled)newValue).getText());
                    System.out.println("oldValue: " + oldValue + "    newValue: " + newValue);
                }
            }
        });

        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
