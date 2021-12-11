package com.udu3324.tasks;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MoveAcceptedReport {
    // Class Info - .move is a void and moves the report to confirmed.txt
    public static void move(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        File myObj = new File("unconfirmed.txt");

        String[] unconfirmedContents = TXTasArray.get("unconfirmed.txt");
        if (Arrays.asList(unconfirmedContents).contains(uuid)) {
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
                int indexOfUUID = Arrays.asList(unconfirmedContents).indexOf(uuid);
                FileWriter myWriter = new FileWriter("confirmed.txt", true);
                //write uuid, stolen, and proof to confirmed.txt
                myWriter.write(unconfirmedContents[indexOfUUID] + "\n" + unconfirmedContents[indexOfUUID + 1] + "\n" + unconfirmedContents[indexOfUUID + 2] + "\n");
                myWriter.close();
                //remove uuid, stolen, and proof from array
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                try {
                    File myObj2 = new File("unconfirmed.txt");
                    if (myObj2.createNewFile()) {
                        System.out.println("File created: " + myObj2.getName());
                        FileWriter writer = new FileWriter("unconfirmed.txt");
                        //create unconfirmed.txt with new array
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
}
