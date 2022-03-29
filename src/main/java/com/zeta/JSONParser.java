package com.zeta;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JSONParser {

    public static void main(String[] args) {

        String inputJson = "{\n"
                + "\"k1\" : \"v1\",\n"
                + "\"k2\" : [\"v2\", \"v3\", \"\", {\"k21\": \"v21\", \"k22\": \"\"}],\n"
                + "\"k3\" : {\"k4\" : \"v4\", \"k5\" : [\"v5\", \"v6\", null], \"k6\": {\"k7\" : \"v7\", \"k8\" : \"\"}},\n"
                + "\"k9\" : [],\n"
                + "\"k10\" : null,\n"
                + "\"k11\" : [23, true, false, \"hello\"]\n"
                + "}";
        String inputJson2 = "{\n"
                + "\"k9\" : []\n"
                + "}";

        String inputJson3 = "{\n"
                + "\"k1\" : \"v1\",\n"
                + "\"k9\" : [],\n"
                + "\"k10\" : null\n"
                + "}";
        getJSONStringWithBlanks(inputJson);
        getJSONStringWithBlanks(inputJson2);
        getJSONStringWithBlanks(inputJson3);

    }

    public static String getJSONStringWithBlanks(String inputstr) {
        JsonElement jsonElement = JsonParser.parseString(inputstr);
        JsonObject obj = jsonElement.getAsJsonObject();
        System.out.println("Old Json object : " + obj);
        new JSONParser().filterJson(obj);
        System.out.println("New Json object : " +obj);
        return null;
    }

    public void filterJson(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            if (entry.getValue().isJsonArray()) {
                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                if(jsonArray.isEmpty()){
                    iterator.remove();
                }
                Iterator<JsonElement> itr = jsonArray.iterator();
                while (itr.hasNext()){
                    JsonElement element = itr.next();
                    if(element.isJsonObject()){
                        filterJson(element.getAsJsonObject());
                    }else if (element.isJsonNull() || element.getAsString().trim().equals("")){
                        itr.remove();
                    }
                }
            }
            if (entry.getValue().isJsonObject()) {
                filterJson(entry.getValue().getAsJsonObject());
            }
            if (!entry.getValue().isJsonObject() && !entry.getValue().isJsonArray()) {
                if (entry.getValue().isJsonNull() || null== entry.getValue()
                        || entry.getValue().getAsString().trim().equals("")) {

                    iterator.remove();
                }
            }
        }
    }
}


