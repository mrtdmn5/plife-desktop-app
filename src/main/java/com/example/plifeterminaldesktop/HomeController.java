package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeController implements Initializable {
    @FXML
    private TableView<AddTableItems> ordersTable;

    @FXML
    private TableColumn<AddTableItems, String> ordersDate;

    @FXML
    private TableColumn<AddTableItems, String> ordersOrderNo;

    @FXML
    private TableColumn<AddTableItems, Double> ordersPrice;

    @FXML
    private TableColumn<AddTableItems, String> ordersProductionName;

    @FXML
    private TableColumn<AddTableItems, Integer> ordersQuantity;


    @FXML
    private TableColumn<AddTableItems, String> ordersApprove;


    @FXML
    private TableView<AddTableItems> ordersHistoryTable;

    @FXML
    private TableColumn<AddTableItems, String> historyTableOrdersDate;

    @FXML
    private TableColumn<AddTableItems, String> historyTableOrderNo;

    @FXML
    private TableColumn<AddTableItems, Double> historyTableOrdersPrice;

    @FXML
    private TableColumn<AddTableItems, String> historyTableOrderProductionName;

    @FXML
    private TableColumn<AddTableItems, Integer> historyTableOrdersQuantity;


    @FXML
    private TableColumn<AddTableItems, String> historyTableOrdersApprove;

    ObservableList<AddTableItems> list = FXCollections.observableArrayList(

    );

    public void setTab(TableView ordersHistoryTable) {
        JSONArray historyArr = new JSONArray();
        FileManager fileManager = new FileManager();
        try {
            try {
                historyArr = fileManager.readJsonFile();
            } catch (Exception e) {
                fileManager.writeJsonFile(new JSONArray());
            }
            ObservableList<AddTableItems> list = FXCollections.observableArrayList();
            for (int i = 0; i < historyArr.size(); i++) {
                JSONObject item = (JSONObject) historyArr.get(i);
                list.add(new AddTableItems((String) item.get("date"), (String) item.get("orderNo"), item.get("productName") + "4", Integer.parseInt(item.get("quantity").toString()), Integer.parseInt(item.get("unitPrice").toString())));
            }
            ordersHistoryTable.setItems(list);

        } catch (Exception e) {

            System.out.println("Error on Write History Table" + e);
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addItemsToTable(list);
        addItemsToHistoryTable(list);
        setTab(ordersHistoryTable);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.startServer(ordersTable, ordersHistoryTable);

            }
        }).start();
    }

    public void addItemsToTable(ObservableList list) {

        ordersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        ordersOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        ordersProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productionName"));
        ordersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        ordersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersTable.setItems(list);

    }

    public void addItemsToHistoryTable(ObservableList list) {
        historyTableOrdersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        historyTableOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        historyTableOrderProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productionName"));
        historyTableOrdersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        historyTableOrdersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersHistoryTable.setItems(list);

    }


}


