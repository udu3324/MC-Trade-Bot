package com.udu3324.commands.staff;

import com.udu3324.main.Data;
import com.udu3324.tasks.DeleteRejectedReport;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Reject extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong Syntax!");
        eb.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Data.command + "reject [player-ign/uuid]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Data.command + "reject NintendoOS \n" +
                Data.command + "reject edd3eaa1-31db-4faf-903e-fbcfd3b501d3 \n" +
                Data.command + "reject edd3eaa131db4faf903efbcfd3b501d3 ");
        eb.setColor(new Color(0x6F2EBB));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message reject = event.getMessage();
        if (reject.getContentRaw().contains(Data.command + "reject") && isStaffMember) {
            String input = reject.getContentRaw();
            if (input.length() > 8) {
                try {
                    String ignOrUUID = input.substring(8);
                    String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);
                    if (checkForIGN.equals("Not a IGN or UUID!")) {
                        reject.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                        reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                    } else {
                        String uuid = com.udu3324.api.UUID.find(checkForIGN);
                        DeleteRejectedReport.delete(uuid);
                        EmbedBuilder eb2 = new EmbedBuilder();
                        eb2.setTitle("Rejected scammer report for " + checkForIGN + ".");
                        eb2.setDescription("Your report is not satisfactory. Please redo it again. (staff please ping person that made the report and type the reason)");
                        eb2.setFooter("To report another scammer, do \"" + Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]\" again.");
                        eb2.setColor(new Color(0xBB2F2D));
                        reject.reply(eb2.build()).queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                reject.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            }
        } else if (reject.getContentRaw().equals(Data.command + "reject") && isStaffMember) {
            reject.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        } else if (reject.getContentRaw().contains(Data.command + "reject") && !isStaffMember) {
            reject.reply("Hey! You are not allowed to use this command.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }
}
