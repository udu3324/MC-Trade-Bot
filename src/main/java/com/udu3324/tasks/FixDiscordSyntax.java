package com.udu3324.tasks;

public class FixDiscordSyntax {
    public static String fix(String str) {
        if (str.contains("_")) {
            StringBuilder stringFix = new StringBuilder(str);
            int underscoreIndex;
            int underscores;
            underscores = str.length() - str.replace("_", "").length();
            underscoreIndex = stringFix.indexOf("_");
            if (underscores > 1) {
                int count = 0;
                do {
                    stringFix.insert(underscoreIndex, "\\");
                    underscoreIndex = stringFix.indexOf("_", underscoreIndex + 2);
                    count++;
                } while (count != underscores);
            } else {
                stringFix.insert(underscoreIndex, "\\");
            }
            str = stringFix.toString();
        }
        return str;
    }
}
