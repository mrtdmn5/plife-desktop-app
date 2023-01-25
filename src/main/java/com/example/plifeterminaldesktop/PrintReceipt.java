package com.example.plifeterminaldesktop;

import javafx.collections.ObservableSet;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class PrintReceipt {

    public void createPrinterItems(String receipt){

        ObservableSet<Printer> printers = Printer.getAllPrinters();
        Printer defaultprinter = Printer.getDefaultPrinter();
        System.out.println(defaultprinter);

        Label text = new Label();
        text.setMaxSize(140 ,-1);
        text.setWrapText(false);
        text.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 8;");
        text.setText(receipt);
        System.out.println("----------");

        System.out.println(text.getText());
        print(text);

    }


    private static void print(Node node)
    {


        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null)
        {

            System.out.println(job.jobStatusProperty().asString());


            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A0, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);

            boolean printed = job.printPage(pageLayout,node);

            if (printed)
            {

                job.endJob();
            }
            else
            {
                System.out.println("Printing failed.!!!");
            }
        }
        else
        {
            // Write Error Message
            System.out.println("Could not create a printer job.!!");
        }
    }
}
