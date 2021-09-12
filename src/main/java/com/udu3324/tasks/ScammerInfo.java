package com.udu3324.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScammerInfo {
    public static String[] get(String uuid) {
        String[] str = new String[2];
        try {
            uuid = uuid.replace("-", "");
            File myObj = new File("data/" + uuid + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                str[0] = myReader.nextLine();
                str[1] = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return str;
    }
}
