package com.udu3324.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IGN {
    public static String find(String find) throws Exception {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + find;
        URL obj = new URL(url);
        if (url.contains(" ") || url.contains(">")) {
            return "Not a IGN or UUID!";
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
        if (responseCode != 200) {
            System.out.println("Not a IGN! Now trying UUID.");
            return uuidToIGN(find);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response2 = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response2.append(inputLine);
            }
            int idIndex = response2.indexOf("\"name\":\"") + 8;
            int id2Index = response2.indexOf("\",\"id\":\"");
            response2 = new StringBuilder(response2.substring(idIndex, id2Index));

            return response2.toString();
        }
    }

    private static String uuidToIGN(String find) throws Exception {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + find;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
        if (responseCode != 200) {
            return "Not a IGN or UUID!";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response2 = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }

        int idIndex = response2.indexOf("\"name\" : \"") + 10;
        int id2Index = response2.indexOf("\",  \"", idIndex + 1);
        response2 = new StringBuilder(response2.substring(idIndex, id2Index));


        in.close();
        return response2.toString();
    }
}
