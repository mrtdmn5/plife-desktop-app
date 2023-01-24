package com.example.plifeterminaldesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.FileWriter;

public class SettingsController {

    @FXML
    private Label yourIpLabel;

    @FXML
    private TextField newPortTextField;

    @FXML
    private Label errorLabelForPort;




    public void initialize() {
        newPortTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                errorLabelForPort.setText("");
                if (!newValue.matches("\\d*")) {

                    newPortTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    System.out.println("dogru" + newValue.toString());
                } else {
                    String port = newPortTextField.getText();
                    if (!port.isBlank() && !port.isEmpty()) {
                        if (Integer.parseInt(port) > 65535) {
                            newPortTextField.setText(newValue.replaceAll("\\d*", ""));
                            errorLabelForPort.setText("Port number must be less than 65535 !");
                            errorLabelForPort.setTextFill(Color.web("#ff0000"));
                            newPortTextField.requestFocus();
                            System.out.println("buyuk");
                        }
                    }
                    System.out.println("yanlis");

                }
            }
        });

    }

    public void saveSettingsAction(ActionEvent actionEvent) {

        String port = newPortTextField.getText();
        if (!port.isBlank() && !port.isEmpty()) {

            System.out.println("dolu");


//        try {
//            FileWriter file = new FileWriter("port.json");
//            file.write(port);
//            file.close();
//        } catch (Exception er) {
//            er.printStackTrace();
//        }
        } else {
            System.out.println("bos");
        }

    }

    public void discardSettingsAction(ActionEvent actionEvent) {
        System.out.println("discardSettingsAction");

    }
}
