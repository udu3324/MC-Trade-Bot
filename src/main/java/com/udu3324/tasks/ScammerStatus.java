package com.udu3324.tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScammerStatus {
    public static boolean get(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        return Arrays.asList(str()).contains(uuid);
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
