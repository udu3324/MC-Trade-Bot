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

public class Reject extends ListenerAdapter {
    //reject command
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong Syntax!");
        eb.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Config.prefix + "reject [player-ign/uuid]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Config.prefix + "reject Notch \n" +
                Config.prefix + "reject 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                Config.prefix + "reject 069a79f444e94726a5befca90e38aaf5 ");
        eb.setColor(new Color(0x6F2EBB));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message reject = event.getMessage();
        String input = reject.getContentRaw();

        // ignore bots
        if (reject.getAuthor().isBot()) {
            return;
        }

        // disable use for other people
        if (input.contains(Config.prefix + "reject") && !isStaffMember) {
            reject.reply("Hey! You are not allowed to use this command.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        // no args
        if (input.equals(Config.prefix + "reject")) {
            reject.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        if (input.contains(Config.prefix + "reject") && isStaffMember) {
            if (input.length() <= 8) {
                reject.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                return;
            }

            try {
                String ignOrUUID = input.substring(8);
                String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);
                if (checkForIGN.equals("Not a IGN or UUID!")) {
                    reject.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                } else {
                    String uuid = com.udu3324.api.UUID.find(checkForIGN);

                    String result = DeleteReport.delete(uuid, "unconfirmed.txt");
                    switch (result) {
                        case "good":
                            EmbedBuilder eb2 = new EmbedBuilder();
                            eb2.setTitle("Rejected scammer report for " + checkForIGN + ".");
                            eb2.setDescription("Your report is not satisfactory. Submit a better one. ");
                            eb2.setThumbnail("https://crafatar.com/renders/body/" + uuid + "?overlay");
                            eb2.setFooter(Config.prefix + "report [ign/uuid] - [scammed items] - [vid url]");
                            eb2.setColor(new Color(0xBB2F2D));
                            reject.reply(eb2.build()).queue();
                            break;
                        case "report not found":
                            reject.reply("Report of `" + checkForIGN + "` couldn't be found.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                        case "bad":
                            reject.reply("Could not reject report of `" + checkForIGN + "`. (create a github bug report) ").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                        default:
                            reject.reply("Could not reject report of `" + checkForIGN + "`. (create a github bug report) \n" + result).queue();
                            reject.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
