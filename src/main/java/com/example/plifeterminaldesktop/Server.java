package com.example.plifeterminaldesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.TextFlow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void startServer(TableView ordersTable, TableView ordersHistoryTable, TextFlow textFlow) {


        try {
            String input = null, output;
            Path fileName= Path.of("port.json");
            String port = Files.readString(fileName);
            HomeController homeController = new HomeController();
            Receipt receiptClass = new Receipt();
            PrintReceipt printReceiptClass = new PrintReceipt();
            Additional additionalClass=new Additional();
            FileManager fileManager = new FileManager();
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
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

                        System.out.println(cartArr);


                        ObservableList<AddTableItems> list = FXCollections.observableArrayList();
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
                            JSONObject item = (JSONObject) cartArr.get(i);
                            String productName = (String) item.get("productName");
                            int quantity = Integer.parseInt(item.get("quantity").toString());
                            int price = Integer.parseInt(item.get("unitPrice").toString());

                            item.put("date", date.toString());
                            item.put("orderNo", orderNo);
                            historyArr.add(item);
                            boolean hasAdditional=item.get("hasAdditional").toString().equals("true")?true:false;
                            System.out.println("**");
                            System.out.println(item.get("hasAdditional"));
                            System.out.println(hasAdditional);
                            System.out.println("**");
                            list.add(new AddTableItems((String) item.get("date"), orderNo, productName, quantity, price,hasAdditional?item.get("selectedAdditional").toString():"None"));

                            total = Integer.parseInt(item.get("unitPrice").toString()) + total;
                            receipt = receiptClass.createReceiptItems(receipt, productName, String.valueOf(quantity), String.valueOf(price));

                        }




                        String printText = receiptClass.createReceipt(date, receipt, total);
                        printReceiptClass.createPrinterItems(printText);


                        fileManager.writeJsonFile(historyArr);
                        homeController.setTab(ordersHistoryTable);


                        ordersTable.setItems(list);
                        additionalClass.getAdditionalItems(ordersTable,textFlow);


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
