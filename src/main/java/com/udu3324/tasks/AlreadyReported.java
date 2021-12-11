package com.udu3324.tasks;

import com.udu3324.main.Data;

import java.io.IOException;
import java.util.Arrays;

public class AlreadyReported {
    //checks if there's already a report about someone
    public static boolean get(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        String[] str = TXTasArray.get("unconfirmed.txt");
        if (Data.mwMode) {
            String[] str2 = TXTasArray.get("mwdiscord.txt");
            return Arrays.asList(str).contains(uuid) || Arrays.asList(str2).contains(uuid);
        }
        return Arrays.asList(str).contains(uuid);
    }
}
