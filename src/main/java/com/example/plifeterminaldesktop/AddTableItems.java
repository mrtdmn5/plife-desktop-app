package com.example.plifeterminaldesktop;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTableItems implements Initializable {
    private String date;
    private  String orderNo;
    private  String productName;

    private  String add;
    private  int quantity;
    private  double price;
    private Button accept;
    private Button cancel;

    public AddTableItems(String date, String orderNo, String productName, int quantity, double price, String add, Button accept,Button cancel) {
        this.date =date;
        this.orderNo =orderNo;
        this.productName =productName;
        this.quantity =quantity;
        this.price =price;
        this.add =add;
        this.accept =accept;
        this.cancel =cancel;

    }
    public String getDate() {
        return  date;
    }
    public String getAdd() {
        return  add;
    }
    public String getOrderNo() {
        return  orderNo;
    }
    public String getProductName() {
        return  productName;
    }
    public int getQuantity() {
        return  quantity;
    }
    public double getPrice() {
        return  price;
    }

    public Button getAccept() {
        return  accept;
    }

    public Button getCancel() {
        return  cancel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}







