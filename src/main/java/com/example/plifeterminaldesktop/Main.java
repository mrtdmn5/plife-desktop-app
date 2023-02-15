package com.example.plifeterminaldesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {
    public static ExecutorService executorService =  Executors.newFixedThreadPool(3);
    public static ServerSocket serverSocket;
    public static Socket connectionSocket;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-layout.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(), 1073 , 611);
        Scene scene = new Scene(fxmlLoader.load(), 1500 , 800);
        stage.setTitle("Plife Terminal!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            executorService.shutdown();
            try {



                if(connectionSocket != null)
                {
                    connectionSocket.close();
                    serverSocket.close();
                    serverSocket=null;
                    connectionSocket=null;
                }

            } catch (SocketException e) {

                // e.printStackTrace();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

            // System.out.println("expected shutdown");
        });

    }

    public static void main(String[] args) {

        launch();
    }
}
