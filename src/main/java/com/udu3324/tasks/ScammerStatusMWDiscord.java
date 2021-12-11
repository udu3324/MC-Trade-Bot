package com.udu3324.tasks;

import java.io.IOException;
import java.util.Arrays;

public class ScammerStatusMWDiscord {
    //Class Info - .get returns if uuid is found as a scammer in the mw discord
    public static boolean get(String uuid) throws IOException {
        uuid = uuid.replace("-", "");
        return Arrays.asList(TXTasArray.get("mwdiscord.txt")).contains(uuid);
    }
}
