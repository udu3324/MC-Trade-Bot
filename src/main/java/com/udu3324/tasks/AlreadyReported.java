package com.udu3324.tasks;

import java.io.File;
import java.util.Arrays;

public class AlreadyReported {
    public static boolean get(String uuid) {
        File myObj = new File("unconfirmed");
        String[] list = myObj.list();
        String list2 = Arrays.toString(list);

        list2 = list2.replace("-", "");
        uuid = uuid.replace("-", "");
        return list2.contains(uuid + ".txt");
    }
}
