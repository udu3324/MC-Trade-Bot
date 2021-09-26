package com.udu3324.tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScammerInfo {
    public static String[] get(String uuid) throws IOException {
        String[] str = new String[2];
        String[] data = str();
        int getIndex = Arrays.asList(str()).indexOf(uuid);
        str[0] = data[getIndex + 1]; //what they stole
        str[1] = data[getIndex + 2]; //proof
        return str;
    }

    private static String[] str() throws IOException {
        FileReader fileReader = new FileReader("confirmed.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[0]);
    }
}
