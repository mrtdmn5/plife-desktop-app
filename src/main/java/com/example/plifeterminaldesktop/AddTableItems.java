package com.example.plifeterminaldesktop;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTableItems implements Initializable {
    private String date;
    private  String orderNo;
    private  String productName;
    private  int quantity;
    private  double price;

    public AddTableItems(String date, String orderNo, String productName,int quantity,double price) {
        this.date =date;
        this.orderNo =orderNo;
        this.productName =productName;
        this.quantity =quantity;
        this.price =price;

    }
    public String getDate() {
        return  date;
    }
    public String getOrderNo() {
        return  orderNo;
    }
    public String getProductionName() {
        return  productName;
    }
    public int getQuantity() {
        return  quantity;
    }
    public double getPrice() {
        return  price;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}




