package com.example.plifeterminaldesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400 , 400);
        stage.setTitle("Plife Terminal!");
        stage.setScene(scene);
        stage.show();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Server server= new Server();
//                server.startServer();
//
//            }
//        }).start();




    }

    public static void main(String[] args) {
        launch();
    }
}
