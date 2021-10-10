package com.udu3324.tasks;

public class FixDiscordSyntax {
    // Class Info - .fix returns "_" to "\_" to stop underlining in discord
    public static String fix(String str) {
        if (str.contains("_")) {
            StringBuilder stringFix = new StringBuilder(str);
            //get how many _ are in the string
            int underscoreIndex;
            int underscores;
            underscores = str.length() - str.replace("_", "").length();
            underscoreIndex = stringFix.indexOf("_");
            if (underscores > 1) {
                int count = 0;
                //run a loop to replace all _ with \_
                do {
                    stringFix.insert(underscoreIndex, "\\");
                    underscoreIndex = stringFix.indexOf("_", underscoreIndex + 2);
                    count++;
                } while (count != underscores);
            } else {
                //run this if there is 1 or no _ to replace
                stringFix.insert(underscoreIndex, "\\");
            }
            str = stringFix.toString();
        }
        return str;
    }
}
