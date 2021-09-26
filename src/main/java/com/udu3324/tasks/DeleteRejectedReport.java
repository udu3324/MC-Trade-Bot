package com.udu3324.tasks;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteRejectedReport {
    public static void delete(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        File myObj = new File("unconfirmed.txt");

        String[] unconfirmedContents = str();
        if (Arrays.asList(unconfirmedContents).contains(uuid)) {
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
                int indexOfUUID = Arrays.asList(unconfirmedContents).indexOf(uuid);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                try {
                    File myObj2 = new File("unconfirmed.txt");
                    if (myObj2.createNewFile()) {
                        System.out.println("File created: " + myObj2.getName());
                        FileWriter writer = new FileWriter("unconfirmed.txt");
                        for (String str : unconfirmedContents) {
                            writer.write(str + System.lineSeparator());
                        }
                        writer.close();
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
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
}
