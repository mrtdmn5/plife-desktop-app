package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void startServer(TableView ordersTable, TableView ordersHistoryTable) {


        try {
            String input = null, output;
            HomeController homeController = new HomeController();
            Receipt receiptClass = new Receipt();
            PrintReceipt printReceiptClass = new PrintReceipt();
            FileManager fileManager = new FileManager();
            ServerSocket serverSocket = new ServerSocket(6402);
            Socket connectionSocket = serverSocket.accept();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            Scanner inFromClient = new Scanner(connectionSocket.getInputStream());
            PrintWriter outFromServer = new PrintWriter(connectionSocket.getOutputStream(), true);
            Scanner inFromServer = new Scanner(System.in);
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

                        ObservableList<AddTableItems> list = FXCollections.observableArrayList();
                        JSONObject orderNoObj = (JSONObject) cartArr.get(cartArr.size() - 1);
                        String orderNo = orderNoObj.get("orderNo").toString();

                        JSONArray historyArr = new JSONArray();
                        try {
                            historyArr = (JSONArray) jsonParser.parse(new FileReader("D:/output.json"));

                        } catch (Exception e) {
                            FileWriter file = new FileWriter("D:/output.json");
                            file.write("");
                            file.close();
                        }
                        String receipt = new String();
                        int total = 0;
                        for (int i = 0; i < cartArr.size() - 1; i++) {
                            JSONObject item = (JSONObject) cartArr.get(i);
                            String productName = (String) item.get("productName");
                            int quantity = Integer.parseInt(item.get("quantity").toString());
                            int price = Integer.parseInt(item.get("unitPrice").toString());

                            item.put("date", date.toString());
                            item.put("orderNo", orderNo);
                            historyArr.add(item);
                            list.add(new AddTableItems((String) item.get("date"), orderNo, productName, quantity, price));

                            total = Integer.parseInt(item.get("unitPrice").toString()) + total;
                            receipt = receiptClass.createReceiptItems(receipt, productName, String.valueOf(quantity), String.valueOf(price));

                        }

                        String printText = receiptClass.createReceipt(date, receipt, total);
                        printReceiptClass.createPrinterItems(printText);


                        fileManager.writeJsonFile(historyArr);
                        homeController.setTab(ordersHistoryTable);


                        ordersTable.setItems(list);
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
