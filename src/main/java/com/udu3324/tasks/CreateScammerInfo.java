package com.udu3324.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateScammerInfo {
    public static void create(String uuid, String whatTheyStole, String videoLink) {
        try {
            uuid = uuid.replace("-", "");
            File myObj = new File("unconfirmed/" + uuid + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(myObj);
                myWriter.write(whatTheyStole + "\n" + videoLink);
                myWriter.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
