package com.example.plifeterminaldesktop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.net.Inet4Address;


public class SettingsController {

    @FXML
    private Label yourIpLabel;

    @FXML
    private TextField newPortTextField;

    @FXML
    private Label errorLabelForPort;


    public void initialize() {
        try {
            yourIpLabel.setText(Inet4Address.getLocalHost().getHostAddress());

        } catch (Exception e) {

        }
        newPortTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                errorLabelForPort.setText("");
                if (!newValue.matches("\\d*")) {

                    newPortTextField.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    String port = newPortTextField.getText();
                    if (!port.isBlank() && !port.isEmpty()) {
                        if (Integer.parseInt(port) > 65535) {
                            newPortTextField.setText(newValue.replaceAll("\\d*", ""));
                            errorLabelForPort.setText("Port number must be less than 65535 !");
                            errorLabelForPort.setTextFill(Color.web("#ff0000"));
                            newPortTextField.requestFocus();
                        }
                    }

                }
            }
        });

    }

    public void saveSettingsAction(ActionEvent actionEvent) {

        String port = newPortTextField.getText();
        if (!port.isBlank() && !port.isEmpty()) {
            try {
                FileWriter file = new FileWriter("port.json");
                file.write(port);
                file.close();
                errorLabelForPort.setText("Saved..");
                errorLabelForPort.setTextFill(Color.web("#228B22"));
                delay(500, () -> ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close());

            } catch (Exception er) {
                errorLabelForPort.setText("Try another port..");
                errorLabelForPort.setTextFill(Color.web("#ff0000"));
                er.printStackTrace();
            }
        } else {
            errorLabelForPort.setText("Port cannot be empty..");
            errorLabelForPort.setTextFill(Color.web("#ff0000"));
            System.out.println("bos");
        }

    }

    public void discardSettingsAction(ActionEvent actionEvent) {
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();

    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                    ///Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run()

        );

        new Thread(sleeper).start();
    }
}
