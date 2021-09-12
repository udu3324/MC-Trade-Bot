package com.udu3324.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveAcceptedReport {
    public static void move(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        Files.move(Paths.get("unconfirmed/" + uuid + ".txt"), Paths.get("data/" + uuid + ".txt"), StandardCopyOption.REPLACE_EXISTING);
    }
}
