package com.example.plifeterminaldesktop;

import javafx.collections.ObservableSet;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class PrintReceipt {

    public void createPrinterItems(String receipt){

        ObservableSet<Printer> printers = Printer.getAllPrinters();
        Printer defaultprinter = Printer.getDefaultPrinter();
        Label text = new Label();
        text.setMaxSize(140 ,1000);
        text.setWrapText(true);
        text.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 8;");
        text.setText(receipt);
        print(text);

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
}
