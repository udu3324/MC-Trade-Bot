package com.udu3324.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IGNHistory {
    public static JsonArray find(String find) throws Exception {
        String url = "https://api.mojang.com/user/profiles/" + find + "/names";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);

        if (responseCode != 200) {
            System.out.println("Not a UUID! Now trying MC IGN.");
            return nameHistoryIGN(find);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return JsonParser.parseString(response.toString()).getAsJsonArray();
    }

    private static JsonArray nameHistoryIGN(String find) throws Exception {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + find;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
        if (responseCode != 200) {
            return JsonParser.parseString("response").getAsJsonArray();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response2 = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }
        in.close();
        int idIndex = response2.indexOf("\",\"id\":\"") + 8;
        response2 = new StringBuilder(response2.substring(idIndex, idIndex + 32));

        String url2 = "https://api.mojang.com/user/profiles/" + response2 + "/names";
        URL obj2 = new URL(url2);
        HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
        con2.setRequestMethod("GET");
        int responseCode2 = con2.getResponseCode();
        System.out.println("Request Type: " + con2.getRequestMethod() + " | Response Code: " + responseCode2 + " | URL Requested " + url2);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
        String inputLine2;
        StringBuilder response = new StringBuilder();
        while ((inputLine2 = in2.readLine()) != null) {
            response.append(inputLine2);
        }
        in2.close();

        return JsonParser.parseString(response.toString()).getAsJsonArray();
    }
}
