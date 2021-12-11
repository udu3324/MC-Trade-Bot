package com.udu3324.tasks;

import java.io.IOException;
import java.util.Arrays;

public class ScammerStatusDatabase {
    //Class Info - .get returns if uuid is a scammer in the database
    public static boolean get(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        return Arrays.asList(TXTasArray.get("confirmed.txt")).contains(uuid);
    }
}
