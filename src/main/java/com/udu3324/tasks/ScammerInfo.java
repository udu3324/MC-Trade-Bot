package com.udu3324.tasks;

import java.io.IOException;
import java.util.Arrays;

public class ScammerInfo {
    // Class Info - .get returns reason and proof for uuid
    public static String[] get(String uuid) throws IOException {
        String[] str = new String[2];
        String[] data = TXTasArray.get("confirmed.txt"); //array of contents of confirmed.txt file
        // gets the index of uuid inside the array
        int getIndex = Arrays.asList(TXTasArray.get("confirmed.txt")).indexOf(uuid);
        str[0] = data[getIndex + 1]; //what they stole
        str[1] = data[getIndex + 2]; //proof

        return str;
    }
}
