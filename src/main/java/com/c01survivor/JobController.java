package com.c01survivor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JobController {

    public JobController() throws AWTException {
        robot = new Robot();
    }

    @FXML
    private TextArea asciiArtDisplay;
    @FXML
    private Button jobButton;
    @FXML
    private TextField timeInputField;
    @FXML
    private Label resultLabel;

    private Robot robot;
    private Timeline timeline;
    private Timeline longIntervalTimeline;

    private boolean showAsciiArt1 = true;
    private boolean working = false;
    private LocalTime resultTime;


    private final String asciiArt1 = """
                                ___
                                \\ /]
                   _           _(_)
                ___))    I    [  | \\___
                ) //o          | |     \\
             _ (_    >      I  | |      ]
            (O)  \\__<          | | ____/
            [/] /   \\)    I   [__|/_
            [\\]|  ( \\         __/___\\____
            [/]|   \\ \\__  ___|
            [\\]|    \\___E/%%/|___________
            [/]|=====__   (______________
            [\\] \\_____ \\    |
            [/========\\ |   |
            [\\]     []| |   |
                """;

    private final String asciiArt2 = """
                                ___
                                \\ /]
                   _           _(_)
                ___))      I  [  | \\___
                ) //o          | |     \\
             _ (_    >   I     | |      ]
            (O)  \\__<          | | ____/
            [/] /   \\)        [__|/_
            [\\]|  ( \\    I    __/___\\____
            [/]|   \\ \\__  ___|
            [\\]|    \\___E/%%/|___________
            [/]|=====__   (______________
            [\\] \\_____ \\    |
            [/========\\ |   |
            [\\]     []| |   |
                """;

    private final String asciiArt3 = """
                                ___
                             z  \\ /]
                   _           _(_)
                ___))     z   [  | \\___
                ) //-          | |     \\
             _ (_    > z       | |      ]
            (O)  \\__o          | | ____/
            [/] /   \\)        [__|/_
            [\\]|  ( \\         __/___\\____
            [/]|   \\ \\__  ___|
            [\\]|    \\___E/%%/|___________
            [/]|=====__   (______________
            [\\] \\_____ \\    |
            [/========\\ |   |
            [\\]     []| |   |
                     """;


    private final String asciiArt4 = """
                            z   ___
                                \\ /]
                   _        z  _(_)
                ___))         [  | \\___
                ) //-    z     | |     \\
             _ (_    >         | |      ]
            (O)  \\__o          | | ____/
            [/] /   \\)        [__|/_
            [\\]|  ( \\         __/___\\____
            [/]|   \\ \\__  ___|
            [\\]|    \\___E/%%/|___________
            [/]|=====__   (______________
            [\\] \\_____ \\    |
            [/========\\ |   |
            [\\]     []| |   |
                     """;

    @FXML
    protected void buttonClick() {
        if (timeline != null) {
            timeline.stop();
        }
        if (longIntervalTimeline != null) {
            longIntervalTimeline.stop();
        }
        working = !working;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (working) {
                            jobButton.setText("Koniec!");
                            if (showAsciiArt1) {
                                asciiArtDisplay.setText(asciiArt1);
                            } else {
                                asciiArtDisplay.setText(asciiArt2);
                            }
                        } else {
                            jobButton.setText("Do roboty!");

                            if (showAsciiArt1) {
                                asciiArtDisplay.setText(asciiArt3);
                            } else {
                                asciiArtDisplay.setText(asciiArt4);
                            }
                        }
                        showAsciiArt1 = !showAsciiArt1;
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        longIntervalTimeline = new Timeline(
                new KeyFrame(Duration.minutes(3), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (working) {
                            moveMouse();
                            if (resultTime != null){
                            if (resultTime.isBefore(LocalTime.now())){
                                jobButton.fire();
                                showAlert(AlertType.INFORMATION,"INFORMACJA","Zakończono prace!");
                                Toolkit.getDefaultToolkit().beep();
                            }
                            }
                        }
                    }
                })
        );
        longIntervalTimeline.setCycleCount(Timeline.INDEFINITE);
        longIntervalTimeline.play();
    }

    private void moveMouse() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        int x = location.x;
        int y = location.y;
        robot.mouseMove(x + 1, y);
        robot.mouseMove(x, y);
    }

    @FXML
    private void onSaveButtonClick() {
        String timeInput = timeInputField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime inputTime = LocalTime.parse(timeInput, formatter);
            resultTime = inputTime.plusHours(8);
            resultLabel.setText(resultTime.format(formatter));
        } catch (Exception e) {
            showAlert(AlertType.ERROR,"ERROR","Błędny format daty");

        }
    }
    private void showAlert(AlertType alertType,String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
