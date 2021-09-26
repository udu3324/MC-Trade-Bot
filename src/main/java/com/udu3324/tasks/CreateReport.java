package com.udu3324.tasks;

import java.io.FileWriter;
import java.io.IOException;

public class CreateReport {
    //this creates the waiting for approval report, not already accepted report.
    public static void create(String uuid, String whatTheyStole, String videoLink) throws IOException {
        FileWriter myWriter = new FileWriter("unconfirmed.txt", true);
        myWriter.write(uuid + "\n" + whatTheyStole + "\n" + videoLink + "\n");
        myWriter.close();
    }
}
