package com.example.plifeterminaldesktop;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.scene.control.Button;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.NetPermission;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void startServer(TableView ordersTable, TableView ordersHistoryTable, TextFlow textFlow, TableView ordersAcceptedTable, TableView ordersCanceledTable, Label orderCountLabel) {

        try {

            String input = null, output;
            Path fileName = Path.of("port.json");
            String port = Files.readString(fileName);

            HomeController homeController = new HomeController();
            Receipt receiptClass = new Receipt();
            PrintReceipt printReceiptClass = new PrintReceipt();
            Additional additionalClass = new Additional();
            FileManager fileManager = new FileManager();
            AcceptAndCancel acceptAndCancelClass=new AcceptAndCancel();
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
            Socket connectionSocket = serverSocket.accept();
            Scanner inFromClient = new Scanner(connectionSocket.getInputStream());


            while (connectionSocket.isConnected()) {

                try {
                    System.out.println("Server start: ");
                    Date date = new Date();

                    if (inFromClient.hasNextLine()) {
                        input = inFromClient.nextLine();
                        Object object = null;
                        JSONArray cartArr = null;
                        JSONParser jsonParser = new JSONParser();
                        object = jsonParser.parse(input);
                        cartArr = (JSONArray) object;

                        System.out.println(cartArr);


                     //   ObservableList<AddTableItems> newOrders = ordersTable.getItems();
                        ObservableList<AddTableItems> newOrders =  FXCollections.observableArrayList();

                        ObservableList<AddTableItems> oldOrders = ordersTable.getItems();


                        JSONObject orderNoObj = (JSONObject) cartArr.get(cartArr.size() - 1);
                        String orderNo = orderNoObj.get("orderNo").toString();

                        JSONArray historyArr = new JSONArray();
                        try {
                            historyArr = fileManager.readJsonFile();

                        } catch (Exception e) {
                            fileManager.writeJsonFile(historyArr);
                        }
                        String receipt = new String();
                        int total = 0;

                        for (int i = 0; i < cartArr.size() - 1; i++) {

                            Button acceptOrderButton = new Button("Accept");
                            Button cancelOrderButton = new Button("Cancel");

                            JSONObject item = (JSONObject) cartArr.get(i);

                            String productName = (String) item.get("productName");
                            int quantity = Integer.parseInt(item.get("quantity").toString());
                            int price = Integer.parseInt(item.get("unitPrice").toString());
                            item.put("date", date.toString());
                            item.put("orderNo", orderNo);

                            historyArr.add(item);

                            boolean hasAdditional = item.get("hasAdditional").toString().equals("true") ? true : false;

                            AddTableItems tableItems= new AddTableItems((String) item.get("date"), orderNo, productName, quantity, price, hasAdditional ? item.get("selectedAdditional").toString() : "None",acceptOrderButton,cancelOrderButton);
                            AddTableItems acceptedOrCanceledItem= new AddTableItems((String) item.get("date"), orderNo, productName, quantity, price, hasAdditional ? item.get("selectedAdditional").toString() : "None",acceptOrderButton,cancelOrderButton);

                            newOrders.add(tableItems);

                            total = Integer.parseInt(item.get("unitPrice").toString()) + total;
                            receipt = receiptClass.createReceiptItems(receipt, productName, String.valueOf(quantity), String.valueOf(price), hasAdditional ? item.get("selectedAdditional").toString() : null);

                          //  acceptAndCancelClass.buttonsActions(acceptOrderButton,cancelOrderButton,ordersTable,tableItems,ordersAcceptedTable,ordersCanceledTable,textFlow,orderCountLabel);


                            acceptAndCancelClass.buttonsActions(acceptOrderButton,cancelOrderButton,ordersTable,tableItems,ordersAcceptedTable,ordersCanceledTable,textFlow,orderCountLabel,acceptedOrCanceledItem);



                        }

                        String printText = receiptClass.createReceipt(date, receipt, total);
                        printReceiptClass.createPrinterItems(printText);

                        fileManager.writeJsonFile(historyArr);
                       homeController.setTab(ordersHistoryTable);

                       ObservableList finalOrderList=FXCollections.observableArrayList(oldOrders);
                        finalOrderList.addAll(newOrders);
                      //  oldOrders.addAll(newOrders);
                        ordersTable.setItems(finalOrderList);
                        additionalClass.getAdditionalItems(ordersTable, textFlow);
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                orderCountLabel.setText(String.valueOf(ordersTable.getItems().size()));

                            }

                        });


                        if (input.equals("**close**")) {
                            break;
                        }
                    } else {

                        connectionSocket = serverSocket.accept();

                        inFromClient = new Scanner(connectionSocket.getInputStream());

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on server socket 01: " + e);

                }

            }

            // serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error on server socket 02: " + e + e);
        }
    }
}
