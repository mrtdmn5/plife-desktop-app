package com.example.plifeterminaldesktop;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {

    public  void  writeJsonFile(JSONArray historyArr){

        try {
            FileWriter file = new FileWriter("D:/output.json");
            file.write(historyArr.toJSONString());
            file.close();

        }catch (Exception e){

        }

    }
    public  JSONArray readJsonFile(){
        JSONArray historyArr = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            historyArr = (JSONArray) jsonParser.parse(new FileReader("D:/output.json"));

        }catch (Exception e){

        }

        return historyArr;
    }


}
