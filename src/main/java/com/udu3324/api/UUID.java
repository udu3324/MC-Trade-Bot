package com.udu3324.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UUID {
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
        return response2.toString();
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
        } else {
            find = find.replace("-", "");
            return find;
        }
    }
}
