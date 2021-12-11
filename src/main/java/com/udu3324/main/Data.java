package com.udu3324.main;

@SuppressWarnings("CanBeFinal")
public class Data {

    // Command prefix set to
    public static final String command = ">";

    // Discord ID Stuff
    public static String staffRoleID = "100000000000000000";
    public static String guildID = "100000000000000000";
    public static Long reportChannelID = 100000000000000000L;
    public static Long checkChannelID = 100000000000000000L;

    // mw mode is for mw discord
    public static Boolean mwMode = false;

    static {
        //noinspection ConstantConditions
        if (mwMode) {
            staffRoleID = "685665101021577240";
            guildID = "647632646695944192";
            reportChannelID = 684880075752996937L;
            checkChannelID = 897310408224931860L;
        }
    }
}
