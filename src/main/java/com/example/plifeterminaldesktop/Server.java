package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Server {





    public static void startServer(TableView ordersTable) {


        try {
            System.out.println("server start1: ");
            String input = null, output;
            HomeController homeController = new HomeController();
            System.out.println("server start13: ");
            ServerSocket serverSocket = new ServerSocket(6400);
            System.out.println("server start12: ");
            Socket connectionSocket = serverSocket.accept();
            System.out.println("server start:2 ");
            Scanner inFromClient = new Scanner(connectionSocket.getInputStream());
            PrintWriter outFromServer = new PrintWriter(connectionSocket.getOutputStream(), true);
            Scanner inFromServer = new Scanner(System.in);
            System.out.println("server start:2 ");
            while (connectionSocket.isConnected()) {

                try {
                    System.out.println("server start: ");

                    Date date = new Date();

                    if (inFromClient.hasNextLine()) {
                        input = inFromClient.nextLine();

                       // System.out.println("Client: " + input);
                        Object object=null;
                        JSONArray cartArr=null;
                        JSONParser jsonParser=new JSONParser();
                        object=jsonParser.parse(input);
                        cartArr=(JSONArray) object;
                        if(!cartArr.isEmpty()){
                            outFromServer.println(true);
                        }

                        ObservableList<AddTableItems> list = FXCollections.observableArrayList();

                        for (int i=0;i<cartArr.size();i++){
                            JSONObject item= (JSONObject) cartArr.get(i);
                            System.out.println("Json object :: "+item);

                            list.add(new AddTableItems(date, "PS"+i, item.get("productName")+ "4", Integer.parseInt( item.get("quantity").toString()),Integer.parseInt( item.get("unitPrice").toString())));

                        }

                        //      ObservableList<AddTableItems> list = FXCollections.observableArrayList();
                        //    list.add(new AddTableItems("55214541", "P22S01", "Watter" + "444", 32222, 18));
                        //   homeController.addItemsToTable(list);
                        ordersTable.setItems(list);
                        if (input.equals("**close**")) {
                            System.out.println("kopptu: ");
                            break;
                        }
                    } else {
                        System.out.println("else: ");
                        connectionSocket = serverSocket.accept();
                        System.out.println("else: 2");
                        inFromClient = new Scanner(connectionSocket.getInputStream());
                        System.out.println("else: son");
                    }

                } catch (Exception e) {
                    System.out.println("hataa: " + e);
                    System.out.println("hataa: " + inFromClient.nextLine());
                }


                //outFromServer.flush();
                System.out.println("while: in ");
            }

            System.out.println("while: out ");
            // serverSocket.close();
        } catch (Exception e) {
            System.out.println("while: out " + e);
        }
    }
}
