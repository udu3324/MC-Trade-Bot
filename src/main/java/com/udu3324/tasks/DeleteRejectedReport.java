package com.udu3324.tasks;

import java.io.File;

public class DeleteRejectedReport {
    public static void delete(String uuid) {
        uuid = uuid.replace("-", "");
        File myObj = new File("unconfirmed/" + uuid + ".txt");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
