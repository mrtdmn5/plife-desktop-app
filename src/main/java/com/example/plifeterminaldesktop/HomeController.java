package com.example.plifeterminaldesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.scene.control.Button;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.plifeterminaldesktop.Main.executorService;

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
    private TableColumn<AddTableItems, Button> ordersAcceptCol;
    @FXML
    private TableColumn<AddTableItems, Button> ordersCancelCol;

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
    public TableView<AddTableItems> ordersAcceptedTable;

    @FXML
    private TableColumn<AddTableItems, String> acceptedTableOrderNo;

    @FXML
    private TableColumn<AddTableItems, String> acceptedTableOrderProductionName;

    @FXML
    private TableColumn<AddTableItems, String> acceptedTableOrdersDate;

    @FXML
    private TableColumn<AddTableItems, Double> acceptedTableOrdersPrice;

    @FXML
    private TableColumn<AddTableItems, Integer> acceptedTableOrdersQuantity;

    @FXML
    public TableView<AddTableItems> ordersCanceledTable;
    @FXML
    private TableColumn<AddTableItems, String> canceledTableOrderNo;

    @FXML
    private TableColumn<AddTableItems,String> canceledTableOrderProductionName;

    @FXML
    private TableColumn<AddTableItems, String> canceledTableOrdersDate;

    @FXML
    private TableColumn<AddTableItems, Double> canceledTableOrdersPrice;

    @FXML
    private TableColumn<AddTableItems, Integer> canceledTableOrdersQuantity;

    @FXML
    public TextFlow orderDetailsTextFlow;


    @FXML
    public Label orderCount;

    @FXML
    public TabPane mainTabPane;

    @FXML
    public Button clearHistoryButton;


    @FXML
    public Label ordersCountText;




    ObservableList<AddTableItems> list = FXCollections.observableArrayList(

         //   new AddTableItems("asd","ADS","1",2,2,"2",null,null)
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
                boolean hasAdditional=item.get("hasAdditional").toString().equals("true")?true:false;
                list.add(new AddTableItems((String) item.get("date"), (String) item.get("orderNo"), item.get("productName") + "4", Integer.parseInt(item.get("quantity").toString()), Integer.parseInt(item.get("unitPrice").toString()), hasAdditional?item.get("selectedAdditional").toString():"None",new Button(),new Button()));
            }
            ordersHistoryTable.setItems(list);

        } catch (Exception e) {

            System.out.println("Error on Write History Table" + e);
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearHistoryButton.setManaged(false);
        clearHistoryButton.setVisible(false);

        Additional additionalClass=new Additional();
        checkPortFile();
        addItemsToTable(list);
        addItemsToHistoryTable(list);
        addItemsToAcceptedTable(list);
        addItemsToCanceledTable(list);
        setTab(ordersHistoryTable);
        additionalClass.getAdditionalItems(ordersHistoryTable,orderDetailsTextFlow);


        //orderCountTextFlow.getChildren().add(new Label("murattt"));
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab oldTab, Tab newTab) {

                switch(newTab.getId()) {

                    case "ordersTab": {
                        clearHistoryButton.setManaged(false);
                        clearHistoryButton.setVisible(false);

                        ordersCountText.setText("Orders:");
                        ordersCountText.setManaged(true);
                        ordersCountText.setVisible(true);

                        orderCount.setText(String.valueOf(ordersTable.getItems().size()));


                        System.out.println("ordersTab");

                        break;
                    }
                    case "ordersHistoryTab":
                    {
                        clearHistoryButton.setManaged(true);
                        clearHistoryButton.setVisible(true);

                        ordersCountText.setManaged(false);
                        ordersCountText.setVisible(false);

                        orderCount.setText(String.valueOf(ordersHistoryTable.getItems().size()));

                        System.out.println("ordersHistoryTab2");
                        break;
                    }

                    case "ordersAcceptedTab": {
                        clearHistoryButton.setManaged(false);
                        clearHistoryButton.setVisible(false);

                        ordersCountText.setManaged(true);
                        ordersCountText.setVisible(true);
                        ordersCountText.setText("Accepted Order:");

                        orderCount.setText(String.valueOf(ordersAcceptedTable.getItems().size()));


                        System.out.println("ordersAcceptedTab");
                        break;
                    }
                    case "ordersCanceledTab": {
                        clearHistoryButton.setManaged(false);
                        clearHistoryButton.setVisible(false);

                        ordersCountText.setManaged(true);
                        ordersCountText.setVisible(true);
                        ordersCountText.setText("Canceled Order:");

                        orderCount.setText(String.valueOf(ordersCanceledTable.getItems().size()));

                        System.out.println("ordersCanceledTab");
                        break;
                    }
                    default:
                        // code block
                }
              //  System.out.println(newTab.getId());
            }
        });
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.startServer(ordersTable, ordersHistoryTable, orderDetailsTextFlow,ordersAcceptedTable,ordersCanceledTable, orderCount);

            }
        }).start();
        */
        //com.example.plifeterminaldesktop.Main.executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.startServer(ordersTable, ordersHistoryTable, orderDetailsTextFlow,ordersAcceptedTable,ordersCanceledTable, orderCount);
            }
        });

    }

    public void addItemsToTable(ObservableList list) {

        ordersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        ordersOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        ordersProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productName"));
        ordersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        ordersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));
        ordersAcceptCol.setCellValueFactory(new PropertyValueFactory<AddTableItems, Button>("accept"));
        ordersCancelCol.setCellValueFactory(new PropertyValueFactory<AddTableItems, Button>("cancel"));

        ordersTable.setItems(list);

    }

    public void addItemsToHistoryTable(ObservableList list) {
        historyTableOrdersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        historyTableOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        historyTableOrderProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productName"));
        historyTableOrdersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        historyTableOrdersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersHistoryTable.setItems(list);

    }

    public void addItemsToAcceptedTable(ObservableList list) {
        acceptedTableOrdersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        acceptedTableOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        acceptedTableOrderProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productName"));
        acceptedTableOrdersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        acceptedTableOrdersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersAcceptedTable.setItems(list);

    }
    public void addItemsToCanceledTable(ObservableList list) {
        canceledTableOrdersDate.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("date"));
        canceledTableOrderNo.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("orderNo"));
        canceledTableOrderProductionName.setCellValueFactory(new PropertyValueFactory<AddTableItems, String>("productName"));
        canceledTableOrdersQuantity.setCellValueFactory(new PropertyValueFactory<AddTableItems, Integer>("quantity"));
        canceledTableOrdersPrice.setCellValueFactory(new PropertyValueFactory<AddTableItems, Double>("price"));

        ordersCanceledTable.setItems(list);

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
                stageSettings.setTitle("Set New Port");
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

    @FXML
    private void clearHistoryAction(ActionEvent actionEvent){


        try {
            FileManager fileManager = new FileManager();
            fileManager.writeJsonFile(new JSONArray());
            setTab(ordersHistoryTable);
        } catch (Exception e) {

        }
    }




//    public void  setOrderCount(Label orderCountLabel,TableView orderTable){
//
//        orderCountLabel.setText("asdasdasd");
//
//    }
//



}


