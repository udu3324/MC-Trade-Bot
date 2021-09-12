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

public class Info extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Info");
        eb.setDescription("Below is information about roles, how the server works, and more.");
        eb.setColor(new Color(0x4BFF99));
        eb.setTimestamp(new Date().toInstant());
        eb.setFooter("Bot by NintendoOS", null);
        eb.addField("What is this server for?", "This server is for traders on MW to easily check and report players who are suspected of scamming. There is still no guarantee that this server will get you 100% safe.", false);
        eb.addField("Staff", "Staff role represent the staff in the MW Scammer Server.", false);
        eb.addField("Contributor", "Contributor role represent the people that contributed to help make the MW Scammer Bot in some way.", false);
        eb.addField("Server Booster", "Server Booster role represent the Server Boosters that boosted the server.", false);

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message info = event.getMessage();
        if (info.getContentRaw().equals(Data.command + "info") && info.isFromType(ChannelType.TEXT) && isStaffMember) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(eb.build()).queue();
            info.delete().queue();
        }
    }
}
