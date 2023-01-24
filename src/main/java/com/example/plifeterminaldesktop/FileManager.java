package com.example.plifeterminaldesktop;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {

    String path="output.json";
    public  void  writeJsonFile(JSONArray historyArr){

        try {
            FileWriter file = new FileWriter(path);
            file.write(historyArr.toJSONString());
            file.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public  JSONArray readJsonFile(){
        JSONArray historyArr = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            historyArr = (JSONArray) jsonParser.parse(new FileReader(path));

        }catch (Exception e){
            writeJsonFile(historyArr);

        }

        return historyArr;
    }


}
