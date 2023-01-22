package com.example.plifeterminaldesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void startServer(TableView ordersTable,TableView ordersHistoryTable) {



        try {
            String input = null, output;
            HomeController homeController = new HomeController();
            ServerSocket serverSocket = new ServerSocket(6402);
            Socket connectionSocket = serverSocket.accept();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            Scanner inFromClient = new Scanner(connectionSocket.getInputStream());
            PrintWriter outFromServer = new PrintWriter(connectionSocket.getOutputStream(), true);
            Scanner inFromServer = new Scanner(System.in);
            while (connectionSocket.isConnected()) {

                try {
                    System.out.println("Server start: ");
                    Date date = new Date();

                    if (inFromClient.hasNextLine()) {
                        input = inFromClient.nextLine();
                        Object object=null;
                        JSONArray cartArr=null;
                        JSONParser jsonParser=new JSONParser();
                        object=jsonParser.parse(input);
                        cartArr=(JSONArray) object;

                        ObservableList<AddTableItems> list = FXCollections.observableArrayList();
                        JSONObject orderNoObj= (JSONObject) cartArr.get(cartArr.size()-1);
                        String orderNo=orderNoObj.get("orderNo").toString();

                        JSONArray historyArr =new JSONArray();
                        try {
                            historyArr = (JSONArray) jsonParser.parse(new FileReader("D:/output.json"));

                        }catch (Exception e){
                            FileWriter file = new FileWriter("D:/output.json");
                            file.write("");
                            file.close();
                        }
                        String receipt=new String();
                        int total=0;
                        for (int i=0;i<cartArr.size()-1;i++){
                            JSONObject item= (JSONObject) cartArr.get(i);

                            item.put("date",date.toString());
                            item.put("orderNo",orderNo);
                            historyArr.add(item);
                              list.add(new AddTableItems((String) item.get("date"), orderNo, item.get("productName")+ "4", Integer.parseInt( item.get("quantity").toString()),Integer.parseInt( item.get("unitPrice").toString())));

                            total=Integer.parseInt( item.get("unitPrice").toString())+total;
                            receipt = receipt + "\n" + (checkUtf8((String) item.get("productName")) + "      ").substring(0, 9) + ".";
                            ;
                            receipt = receipt + "          " + item.get("quantity");
                            receipt = receipt + "           " + item.get("unitPrice") + " kr";
                        }

                        String printText="\n\n\n" + date + "\n\n" +
                                "Name" + "           " + "Quantity" + "         " + "Price" +
                                "\n" +
                                receipt + "\n\n\n" +
                                "                  " + "Total" + ":" + total + " " + "kr" + "\n" +
                                "                  " + "takeAway" + "\n\n\n\n";

                        ObservableSet<Printer> printers = Printer.getAllPrinters();
                        Printer defaultprinter = Printer.getDefaultPrinter();

                        System.out.println("****");
                        System.out.println(defaultprinter);
                        Label text = new Label();
                        text.setMaxSize(140 ,1000);
                        text.setWrapText(true);
                        // text.autosize();
                        text.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 8;");
//                        String s = "Hello" +"\n"+" how are you Hello " +
//                                "how are you Hello how are you";
                        text.setText(printText);
                        print(text);



                        //Get all Printers
//                        ObservableSet<Printer> printers = Printer.getAllPrinters();
//                        Printer defaultprinter = Printer.getDefaultPrinter();
//
//                        System.out.println("****");
//                        System.out.println(defaultprinter);
//                        Label text = new Label();
//                        text.setMaxSize(220 ,158);
//                        text.setWrapText(true);
//                      // text.autosize();
//                        String s = "Hello how are you Hello how are you Hello how are you";
//                        text.setText(s);
//                        print(text);

//                        TextArea ta = new TextArea();
//                        ta.setPrefRowCount(10);
//                        ta.setPrefColumnCount(20);
//                        ta.setWrapText(true);
//                        ta.appendText("murat  ObservableSet<Printer> printers = Printer.getAllPrinters();\n" +
//                                "                        Printer defaultprinter = Printer.getDefaultPrinter();" );
//                        print(ta);
//                        for(Printer printer : printers)
//                        {
//                            //textArea.appendText(printer.getName()+"\n");
//                            System.out.println(printer.getPrinterAttributes()+"\n");
//                        }



                        FileWriter file = new FileWriter("D:/output.json");
                        file.write(historyArr.toJSONString());
                        file.close();

                        homeController.setTab(ordersHistoryTable);


                        ordersTable.setItems(list);
                        if (input.equals("**close**")) {
                            break;
                        }
                    } else {

                        connectionSocket = serverSocket.accept();

                        inFromClient = new Scanner(connectionSocket.getInputStream());

                    }

                } catch (Exception e) {
                    System.out.println("Error on server socket 01: " + e);

                }

            }

            // serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error on server socket 02: " + e + e);
        }
    }

    private static void print(Node node)
    {

        // Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null)
        {

            System.out.println(job.jobStatusProperty().asString());


            // Print the node
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A6, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            boolean printed = job.printPage(pageLayout,node);

            if (printed)
            {
                // End the printer job
                job.endJob();
            }
            else
            {
                // Write Error Message
                System.out.println("Printing failed.!!!");
            }
        }
        else
        {
            // Write Error Message
            System.out.println("Could not create a printer job.!!");
        }
    }

    public static String checkUtf8(String data) {
        char[] charArray = data.toCharArray();
        char character;
        int castAscii;


        for (int i = 0; i < charArray.length; i++) {
            character = charArray[i];
            castAscii = (int) character;

            if (castAscii >= 224 && castAscii <= 230) {
                charArray[i] = 'a';
            }
            if (castAscii >= 192 && castAscii <= 198) {

                charArray[i] = 'A';
            }


            if (castAscii >= 210 && castAscii <= 214 || castAscii == 216) {
                charArray[i] = 'O';
            }
            if (castAscii >= 240 && castAscii <= 246 || castAscii == 248) {
                charArray[i] = 'o';
            }


        }
        return String.valueOf(charArray);
    }
}
