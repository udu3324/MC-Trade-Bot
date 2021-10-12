package com.udu3324.main;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Objects;

public class Data {
    // JDA Bot Stuff
    public static final String command = ">";
    public static final String activity = "play.minewind.net";

    // MW Scammer Discord
    //private static final String guildID = "883832292466892841";
    //public static final String staffRoleID = "884189692780756992";
    //public static final String reportChannelID = "884189288974139392";
    //public static final String checkChannelID = "884189308959981598";
    //public static TextChannel report = Objects.requireNonNull(MainBot.getJda().getGuildById(guildID)).getTextChannelById(reportChannelID);
    //public static TextChannel check = Objects.requireNonNull(MainBot.getJda().getGuildById(guildID)).getTextChannelById(checkChannelID);

    // MW Discord
    public static final String staffRoleID = "685665101021577240";
    public static final String reportChannelID = "684880075752996937";
    public static final String checkChannelID = "897310408224931860";
    private static final String guildID = "647632646695944192";
    public static TextChannel report = Objects.requireNonNull(MainBot.getJda().getGuildById(guildID)).getTextChannelById(reportChannelID);
    public static TextChannel check = Objects.requireNonNull(MainBot.getJda().getGuildById(guildID)).getTextChannelById(checkChannelID);
}
