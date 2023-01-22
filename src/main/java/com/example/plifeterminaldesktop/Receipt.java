package com.example.plifeterminaldesktop;

import java.util.Date;

public class Receipt {


    public static String createReceiptItems(String receipt,String productName,String quantity,String price){

        System.out.println(productName);
        if(checkUtf8(productName).length()>=9){
            productName=checkUtf8(productName);
            productName= productName.substring(0, 9)+"..";
            productName=(productName + "                  ").substring(0, 17);
        }
        else  if(checkUtf8(productName).length()>=6){
            productName=checkUtf8(productName)+"              ";
            productName=(productName+"                         ").substring(0, 18);
        }
        else {
            productName=checkUtf8(productName)+"              ";
            productName=(productName+"                         ").substring(0, 20);
        }

        if(quantity.length()>=3){


            quantity=("   "+quantity + "                  ").substring(0, 18);
        }
        else  if(quantity.length()==2){
            quantity=("   "+quantity+"                         ").substring(0, 19);
        }
        else {
            quantity=("   "+quantity+"                         ").substring(0, 20);
        }


        System.out.println(productName);
        System.out.println(productName.length());
        receipt = receipt + "\n" + productName;


        receipt = receipt+ quantity;
        receipt = receipt +price + " kr";


return receipt;
    }

    public String createReceipt(Date date,String receipt,int total){
        String printText="\n\n\n" + date + "\n\n" +
                "Name" + "           " + "Quantity" + "         " + "Price" +
                "\n" +
                receipt + "\n\n\n" +
                "                  " + "Total" + ":" + total + " " + "kr" + "\n" +
                "                  " + "Take Away" + "\n\n\n\n";


        return printText;
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
