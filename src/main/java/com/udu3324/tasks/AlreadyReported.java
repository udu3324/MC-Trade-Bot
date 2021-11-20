package com.udu3324.tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlreadyReported {
    //checks if there's already a report about someone
    public static boolean get(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        String[] str = str();
        String[] str2 = str2();
        return Arrays.asList(str).contains(uuid) || Arrays.asList(str2).contains(uuid);
    }

    private static String[] str() throws IOException {
        FileReader fileReader = new FileReader("unconfirmed.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[0]);
    }

    private static String[] str2() throws IOException {
        FileReader fileReader = new FileReader("mwdiscord.txt");
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
