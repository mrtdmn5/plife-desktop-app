package com.example.plifeterminaldesktop;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTableItems implements Initializable {
    private Date date;
    private  String orderNo;
    private  String productionName;
    private  int quantity;
    private  double price;

    public AddTableItems(Date date, String orderNo, String productionName,int quantity,double price) {
        this.date =date;
        this.orderNo =orderNo;
        this.productionName =productionName;
        this.quantity =quantity;
        this.price =price;

    }
    public Date getDate() {
        return  date;
    }
    public String getOrderNo() {
        return  orderNo;
    }
    public String getProductionName() {
        return  productionName;
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




