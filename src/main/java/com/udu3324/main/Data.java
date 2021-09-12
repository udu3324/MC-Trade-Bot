package com.udu3324.main;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Data extends ListenerAdapter {
    public static final String command = ">";
    public static final String reportChannelID = "884189288974139392";
    public static final String checkChannelID = "884189308959981598";
    public static final String staffRoleID = "884189692780756992";
    protected static final String activity = "play.minewind.net";
    public static TextChannel report;
    public static TextChannel check;

    public void onMessageReceived(MessageReceivedEvent event) {
        Message autoSession = event.getMessage();
        if (autoSession.isFromType(ChannelType.TEXT)) {
            report = event.getGuild().getTextChannelById(reportChannelID);
            check = event.getGuild().getTextChannelById(checkChannelID);
        }
    }
}
