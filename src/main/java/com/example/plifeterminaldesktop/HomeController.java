package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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


    @FXML
    public TextFlow orderDetailsTextFlow;


    ObservableList<AddTableItems> list = FXCollections.observableArrayList(

    );

    private Stage stageSettings;

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
                list.add(new AddTableItems((String) item.get("date"), (String) item.get("orderNo"), item.get("productName") + "4", Integer.parseInt(item.get("quantity").toString()), Integer.parseInt(item.get("unitPrice").toString()), "addtionalH"));
            }
            ordersHistoryTable.setItems(list);

        } catch (Exception e) {

            System.out.println("Error on Write History Table" + e);
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkPortFile();
        addItemsToTable(list);
        addItemsToHistoryTable(list);
        setTab(ordersHistoryTable);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.startServer(ordersTable, ordersHistoryTable, orderDetailsTextFlow);

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

    public void checkPortFile() {
        try {

            Path fileName= Path.of("port.json");
            String port = Files.readString(fileName);
            System.out.println("port");
            System.out.println(port);

        } catch (Exception e) {
            try {
                System.out.println("catchPort");
                FileWriter file = new FileWriter("port.json");
                file.write("6402");
                file.close();
            } catch (Exception er) {
                er.printStackTrace();
            }


        }
    }


    @FXML
    private void setPortAction(ActionEvent actionEvent) {


        try {
            if (stageSettings == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-port-layout.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stageSettings = new Stage();
                stageSettings.setTitle("About");

                stageSettings.setScene(new Scene(root1));
                stageSettings.setResizable(false);
                stageSettings.show();
            } else if (stageSettings.isShowing()) {
                stageSettings.toFront();
            } else {
                stageSettings.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}


