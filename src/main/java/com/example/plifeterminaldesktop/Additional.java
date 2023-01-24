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
    @FXML
    private TextFlow orderDetailsTextFlow;
    public void getAdditionalItems(TableView tableView, TextFlow textFlow){


        tableView.getFocusModel().focusedCellProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                textFlow.getChildren().clear();




                System.out.println("changgee");
                ObservableList<AddTableItems> addTableItems;
                addTableItems=tableView.getSelectionModel().getSelectedItems();
                System.out.println(addTableItems.get(0).getAdd());
                if (addTableItems.get(0).getAdd().equals("None")||addTableItems.get(0).getAdd().equals("[]")){
                    Text title=new Text("Additionals\n");
                    textFlow.getChildren().add(title);
                    Text text=new Text("None");
                    textFlow.getChildren().add(text);

                }else {
                    Object object = null;
                    JSONArray additionalArr = null;
                    JSONParser jsonParser = new JSONParser();


                    try {
                        object = jsonParser.parse(addTableItems.get(0).getAdd());
                        additionalArr = (JSONArray) object;
                        for (int i=0;i<additionalArr.size();i++){
                            JSONObject item= (JSONObject) additionalArr.get(i);
                            String additionalTitleName=item.get("parentName").toString();
                            JSONArray additionalItemsArr = (JSONArray) item.get("items");

                            System.out.println(additionalItemsArr);
                            Label title=new Label();
                            title.setText(additionalTitleName+"\n\n");
                            title.setStyle("-fx-font-weight: bold");
                          //  Text text=new Text(additionalTitleName+"\n\n");
                            textFlow.getChildren().add(title);
                            for (int j=0;j<additionalItemsArr.size();j++){
                                System.out.println("itemsss2");
                                JSONObject additionalItemsItem= (JSONObject) additionalItemsArr.get(j);
                                System.out.println(additionalItemsItem);
                                String optionName= additionalItemsItem.get("option_name").toString();

                                Label label=new Label();
                                label.setText(" "+optionName+" ");
                                label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14;");
                                Insets insets =new Insets(1);
                                label.setPadding(insets);

                                label.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
                                textFlow.getChildren().add(label);
                            }
                            Text newLine=new Text("\n\n\n");
                            textFlow.getChildren().add(newLine);

                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


            }
        });

    }
}
