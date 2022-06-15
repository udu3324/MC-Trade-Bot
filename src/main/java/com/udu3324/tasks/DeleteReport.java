package com.udu3324.tasks;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class DeleteReport {
    //Class Info - .delete returns void and deletes reports from a file
    public static String delete(String uuid, String file) throws IOException {
        uuid = uuid.replace("-", "");
        File myObj = new File(file);

        String[] unconfirmedContents = TXTasArray.get(file);
        if (Arrays.asList(unconfirmedContents).contains(uuid)) {
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
                int indexOfUUID = Arrays.asList(unconfirmedContents).indexOf(uuid);
                //remove uuid, stolen, and proof from array
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                unconfirmedContents = ArrayUtils.remove(unconfirmedContents, indexOfUUID);
                try {
                    File myObj2 = new File(file);
                    if (myObj2.createNewFile()) {
                        System.out.println("File created: " + myObj2.getName());
                        FileWriter writer = new FileWriter(file);
                        //write array back to new file
                        for (String str : unconfirmedContents) {
                            writer.write(str + System.lineSeparator());
                        }
                        writer.close();
                        return "good";
                    } else {
                        System.out.println("File already exists.");
                        return "bad";
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                    return e.toString();
                }
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        return "report not found";
    }
}
