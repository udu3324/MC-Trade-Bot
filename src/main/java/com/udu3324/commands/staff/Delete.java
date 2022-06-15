package com.udu3324.commands.staff;

import com.udu3324.main.Config;
import com.udu3324.tasks.DeleteReport;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Delete extends ListenerAdapter {
    //delete command
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong Syntax!");
        eb.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Config.prefix + "delete [player-ign/uuid]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Config.prefix + "delete Notch \n" +
                Config.prefix + "delete 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                Config.prefix + "delete 069a79f444e94726a5befca90e38aaf5 ");
        eb.setColor(new Color(0x6F2EBB));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message delete = event.getMessage();
        String input = delete.getContentRaw();

        // ignore bots
        if (delete.getAuthor().isBot()) {
            return;
        }

        // disable use for other people
        if (input.contains(Config.prefix + "delete") && !isStaffMember) {
            delete.reply("Hey! You are not allowed to use this command.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        // no args
        if (input.equals(Config.prefix + "delete")) {
            delete.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        if (input.contains(Config.prefix + "delete") && isStaffMember) {
            if (input.length() <= 8) {
                delete.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                return;
            }

            try {
                String ignOrUUID = input.substring(8);
                String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);
                if (checkForIGN.equals("Not a IGN or UUID!")) {
                    delete.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                } else {
                    String uuid = com.udu3324.api.UUID.find(checkForIGN);

                    String result = DeleteReport.delete(uuid, "confirmed.txt");
                    switch (result) {
                        case "good":
                            delete.reply("Successfully deleted the report of `" + checkForIGN + "`.").queue();
                            break;
                        case "report not found":
                            delete.reply("Report of `" + checkForIGN + "` couldn't be found. \nUse >reject if you want to delete/reject a report from <#" + Config.reportChannelID + ">").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                            delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                        case "bad":
                            delete.reply("Could not delete report of `" + checkForIGN + "`. (create a github issue) ").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                            delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                        default:
                            delete.reply("Could not delete report of `" + checkForIGN + "`. (create a github issue) \n" + result).queue();
                            delete.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
