package com.example.plifeterminaldesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Additional {

    //https://stackoverflow.com/questions/12737293/how-do-i-resolve-the-java-net-bindexception-address-already-in-use-jvm-bind

    public void getAdditionalItems(TableView orderTable, TextFlow textFlow){


        orderTable.getFocusModel().focusedCellProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                showAdditionalItems(orderTable,textFlow);

            }
        });


    }

    public void  showAdditionalItems(TableView tableView, TextFlow textFlow){

        try {
            textFlow.getChildren().clear();

  //      textFlow.setStyle("-fx-text-alignment: center;");

        ObservableList<AddTableItems> addTableItems;
        addTableItems=tableView.getSelectionModel().getSelectedItems();
        Text additionalTitle=new Text("Additionals\n\n");
        additionalTitle.setStyle("-fx-font-weight: bold");
        additionalTitle.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 24;");

            textFlow.getChildren().add(additionalTitle);




        if (addTableItems.get(0).getAdd().equals("None")||addTableItems.get(0).getAdd().equals("[]")){
            Text text=new Text("None");
            text.setStyle("-fx-font-weight: bold");
            textFlow.getChildren().add(text);


        }else {

            Object object = null;
            JSONArray additionalArr = null;
            JSONParser jsonParser = new JSONParser();


            try {
                object = jsonParser.parse(addTableItems.get(0).getAdd());
                additionalArr = (JSONArray) object;
                for (int i = 0; i < additionalArr.size(); i++) {
                    JSONObject item = (JSONObject) additionalArr.get(i);
                    String additionalTitleName = item.get("parentName").toString();
                    JSONArray additionalItemsArr = (JSONArray) item.get("items");

                    System.out.println(additionalItemsArr);
                    Text title = new Text();
                    title.setText(additionalTitleName + "\n\n");
                    title.setStyle("-fx-font-weight: bold");

                    textFlow.getChildren().add(title);
                    for (int j = 0; j < additionalItemsArr.size(); j++) {

                        JSONObject additionalItemsItem = (JSONObject) additionalItemsArr.get(j);
                        System.out.println(additionalItemsItem);
                        String optionName = additionalItemsItem.get("option_name").toString();

                        Text optionNameText = new Text();
                        optionNameText.setText(optionName + ",  ");
                        optionNameText.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14;");


                        textFlow.getChildren().add(optionNameText);
                    }
                    Text newLine = new Text("\n\n\n");
                    textFlow.getChildren().add(newLine);

                }


            } catch (Exception e) {
                System.out.println("hata");
                //e.printStackTrace();
            }
        }
        }catch (Exception e){
            System.out.println("hata2");
        }
    }
}
