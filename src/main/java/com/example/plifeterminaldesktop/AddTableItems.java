package com.example.plifeterminaldesktop;


import javafx.fxml.Initializable;

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

    public AddTableItems(String date, String orderNo, String productName,int quantity,double price,String add) {
        this.date =date;
        this.orderNo =orderNo;
        this.productName =productName;
        this.quantity =quantity;
        this.price =price;
        this.add =add;

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




