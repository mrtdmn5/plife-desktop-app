package com.example.plifeterminaldesktop;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.TextFlow;

public class AcceptAndCancel {
    Additional additionalClass= new Additional();
    ObservableList acceptedList = FXCollections.observableArrayList();
    ObservableList canceledList = FXCollections.observableArrayList();

   public void buttonsActions(Button acceptButton, Button cancelButton, TableView ordersTableView, Object tableItem, TableView ordersAcceptedTable, TableView ordersCanceledTable, TextFlow textFlow, Label orderCountLabel,Object acceptedOrCanceledItem){

//       System.out.println("buttonsActions");
//       ordersAcceptedTable.getItems().removeAll();
//       ordersCanceledTable.getItems().removeAll();
       acceptButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {


               acceptedItems(ordersTableView,tableItem,ordersAcceptedTable,textFlow,orderCountLabel, acceptedOrCanceledItem);

           }
       });

       cancelButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {

               canceledItems(ordersTableView,tableItem,ordersCanceledTable,textFlow,orderCountLabel, acceptedOrCanceledItem);

           }
       });


    }

    private void acceptedItems(TableView ordersTableView,Object tableItem,TableView ordersAcceptedTable, TextFlow textFlow, Label orderCountLabel,Object acceptedOrCanceledItem){
        System.out.println("accept");

        acceptedList.add(acceptedOrCanceledItem);
        ordersAcceptedTable.setItems(acceptedList);
        ordersTableView.getItems().remove(tableItem);
        additionalClass.getAdditionalItems(ordersAcceptedTable,textFlow);

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                orderCountLabel.setText(String.valueOf(ordersTableView.getItems().size()));

            }

        });

    }

    private void canceledItems(TableView ordersTableView,Object tableItem,TableView ordersCanceledTable,TextFlow textFlow, Label orderCountLabel,Object acceptedOrCanceledItem){
        System.out.println("cancel");

        canceledList.add(acceptedOrCanceledItem);
        ordersCanceledTable.setItems(canceledList);
        ordersTableView.getItems().remove(tableItem);
        additionalClass.getAdditionalItems(ordersCanceledTable,textFlow);

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                orderCountLabel.setText(String.valueOf(ordersTableView.getItems().size()));

            }

        });
    }
}
