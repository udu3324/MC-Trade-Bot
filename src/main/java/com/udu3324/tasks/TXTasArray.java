package com.udu3324.tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TXTasArray {
    //return an array of string lines in a txt file
    public static String[] get(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();

        return lines.toArray(new String[0]);
    }
}
