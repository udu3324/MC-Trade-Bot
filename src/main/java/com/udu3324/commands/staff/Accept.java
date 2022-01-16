package com.udu3324.commands.staff;

import com.udu3324.main.Data;
import com.udu3324.tasks.MoveAcceptedReport;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Accept extends ListenerAdapter {
    //accept command
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong Syntax!");
        eb.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Data.command + "accept [player-ign/uuid]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Data.command + "accept Notch \n" +
                Data.command + "accept 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                Data.command + "accept 069a79f444e94726a5befca90e38aaf5 ");
        eb.setColor(new Color(0x6F2EBB));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message accept = event.getMessage();
        if (accept.getContentRaw().contains(Data.command + "accept") && isStaffMember) {
            String input = accept.getContentRaw();
            if (input.length() > 8) {
                try {
                    String ignOrUUID = input.substring(8);
                    String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);
                    if (checkForIGN.equals("Not a IGN or UUID!")) {
                        accept.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                        accept.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                    } else {
                        String uuid = com.udu3324.api.UUID.find(checkForIGN);
                        MoveAcceptedReport.move(uuid);
                        EmbedBuilder eb2 = new EmbedBuilder();
                        eb2.setTitle("Accepted scammer report for " + checkForIGN + ".");
                        eb2.setDescription("Thank you for your scammer report! It has been accepted and now added onto the scammer list.");
                        eb2.setThumbnail("https://crafatar.com/renders/body/" + uuid + "?overlay");
                        eb2.setFooter(Data.command + "report [ign/uuid] - [scammed items] - [vid url]");
                        eb2.setColor(new Color(0x6ABB00));
                        accept.reply(eb2.build()).queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                accept.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                accept.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            }
        } else if (accept.getContentRaw().equals(Data.command + "accept") && isStaffMember) {
            accept.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            accept.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        } else if (accept.getContentRaw().contains(Data.command + "accept") && !isStaffMember) {
            accept.reply("Hey! You are not allowed to use this command.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            accept.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }
}
