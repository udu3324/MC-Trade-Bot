package com.udu3324.commands.staff;

import com.udu3324.main.Data;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

public class Rules extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Rules");
        eb.setDescription("There are four simple server rules below. Make sure to follow them.\r" +
                "**1,** Follow the discord [TOS](https://discord.com/terms) and [Guidelines](https://discord.com/guidelines).\n" +
                "**2,** Use common sense.\n" +
                "**3,** Advertising discord servers is not allowed. This includes advertising in dms, channels, and more. (unless if its one of the major minewind servers)\n" +
                "**4,** Spamming, using channels for the wrong purpose, and sending inappropriate messages/content in channels or dms is not allowed.\n" +
                "**Punishments**\nFailing to follow any rule listed will result in punishments based on how severely you broken the rule. NintendoOS reserves the power to do anything and what is needed.");
        eb.setColor(new Color(0xBF0000));
        eb.setTimestamp(new Date().toInstant());
        eb.setAuthor("MW Scammer Bot");

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message rules = event.getMessage();
        if (rules.getContentRaw().equals(Data.command + "rules") && rules.isFromType(ChannelType.TEXT) && isStaffMember) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(eb.build()).queue();
            rules.delete().queue();
        }
    }
}
