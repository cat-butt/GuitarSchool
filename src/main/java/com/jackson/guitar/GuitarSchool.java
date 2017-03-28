package com.jackson.guitar;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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
        ScaleDictionary scaleDictionary = ScaleDictionary.createDict();

        ArrayList<String> familyList = scaleDictionary.getFamilyList();

        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);

        int nCount = 0;
        for (String familyName : familyList) {
            VBox vbox = new VBox();
            ArrayList<RadioButton> radioButtons = new ArrayList<>();
            Label l = new Label(familyName);
            l.setFont(Font.font(18));
            vbox.getChildren().add(l);
            for (String s : scaleDictionary.getScaleListUnderFamily(familyName)) {
                RadioButton rbn = new RadioButton(s);
                rbn.setFont(Font.font(14));
                rbn.setToggleGroup(toggleGroup);
                rbn.setUserData(scaleDictionary.getScale(s));
                rbn.setGraphicTextGap(10.5);
                rbn.setSelected("Ionian".equals(s));
                radioButtons.add(rbn);
            }
            vbox.getChildren().addAll(radioButtons);
            vbox.setSpacing(5.0);
            vbox.setPadding(new Insets(8,8,8,8));
            gridPane.add(vbox, nCount++, 1);
        }

        VBox vBox = new VBox();
        ToggleGroup rootToggleGroup = new ToggleGroup();
        ArrayList<RadioButton> radioButtons = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            RadioButton rb = new RadioButton(ScaleDictionary.NOTE_NAMES[i]);
            rb.setSelected(i == 0);
            rb.setFont(Font.font(14));
            rb.setGraphicTextGap(10.5);
            rb.setToggleGroup(rootToggleGroup);
            rb.setUserData(i);
            radioButtons.add(rb);
        }

        vBox.getChildren().addAll(radioButtons);
        gridPane.add(vBox, nCount++, 1);

        VBox vLabelBox = new VBox();
        Label labelScaleName = new Label();
        labelScaleName.setFont(Font.font(20));
        Label labelScaleSteps = new Label();
        labelScaleSteps.setFont(Font.font(20));
        vLabelBox.getChildren().setAll(labelScaleName, labelScaleSteps);

        gridPane.add(vLabelBox, nCount++, 1);


        rootToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (rootToggleGroup.getSelectedToggle() != null) {
                    guitarCanvas.setScaleRoot((int)newValue.getUserData());
                    pianoCanvas.setScaleRoot((int)newValue.getUserData());
                    System.out.println("oldValue: " + oldValue + "    newValue: " + newValue);
                }
            }
        });

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (toggleGroup.getSelectedToggle() != null) {
                    String s = ((Labeled) newValue).getText();
                    guitarCanvas.setScaleType(s);
                    pianoCanvas.setScaleType(s);
                    labelScaleName.setText(s);
                    ArrayList<Integer> steps = (ArrayList)newValue.getUserData();

                    labelScaleSteps.setText(steps.toString());

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
