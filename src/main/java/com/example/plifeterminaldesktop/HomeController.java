package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//    LocalDateTime now = LocalDateTime.now();
    ObservableList<AddTableItems> list = FXCollections.observableArrayList(

    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       addItemsToTable(list);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server= new Server();
                server.startServer(ordersTable);

            }
        }).start();
    }

    public  void  addItemsToTable(ObservableList  list){

        ordersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        ordersOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        ordersProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productionName"));
        ordersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        ordersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersTable.setItems(list);
    }




}


