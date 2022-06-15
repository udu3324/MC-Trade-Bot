package com.udu3324.commands.member;

import com.udu3324.main.Config;
import com.udu3324.tasks.AlreadyReported;
import com.udu3324.tasks.CreateReport;
import com.udu3324.tasks.ScammerStatusDatabase;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Report extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder syntaxInvalid = new EmbedBuilder();
        syntaxInvalid.setTitle("Wrong Syntax!");
        syntaxInvalid.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Config.prefix + "report [player-ign/uuid] - [what they scammed] - [youtube link]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Config.prefix + "report Notch - dirt - https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" +
                Config.prefix + "report 069a79f4-44e9-4726-a5be-fca90e38aaf5 - golden apples - https://www.youtube.com/watch?v=D1qU745zMKU \n" +
                Config.prefix + "report 069a79f444e94726a5befca90e38aaf5 - diamond block - https://www.youtube.com/watch?v=zYUvgxwuJbA ");
        syntaxInvalid.setColor(new Color(0x6F2EBB));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        Message report = event.getMessage();
        MessageChannel channel = event.getChannel();
        String input = report.getContentRaw();

        //disable chatting in the report channel
        if (!input.contains(Config.prefix + "report")) {
            if (channel.getId().equals(Config.reportChannelID) && !event.getAuthor().isBot() && !isStaffMember && !input.contains(Config.prefix + "reject") && !input.contains(Config.prefix + "accept") && !input.contains(Config.prefix + "delete")) {
                channel.sendMessage("Hey! You aren't allowed to talk here unless if you're submitting a scammer report.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                channel.sendMessage(syntaxInvalid.setTitle("Use this channel correctly!").build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            }
            return;
        }

        //check channel
        if (!(channel.getId().equals(Config.reportChannelID))) {
            channel.sendMessage("Hey! You can only use that command in <#" + Config.reportChannelID + ">. ").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            report.delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

        //check no args
        if (input.equals(Config.prefix + "report")) {
            report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        //check no command (but in channel)
        if (!input.contains(Config.prefix + "report ")) {
            report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            return;
        }

        try {
            int firstArgumentSeparator = input.indexOf(" - ");
            int count = input.split(" - ", -1).length-1;

            if (count != 2) {
                report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                report.delete().queueAfter(3000, TimeUnit.MILLISECONDS);
                return;
            }

            String ignOrUUID = input.substring(8, firstArgumentSeparator);
            String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);

            //filter out reports with incorrect IGNs
            if (checkForIGN.equals("Not a IGN or UUID!")) {
                report.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                return;
            }

            String UUID = com.udu3324.api.UUID.find(checkForIGN);

            //a lot of boolean checks
            boolean isScammerInDatabase = ScammerStatusDatabase.get(UUID);
            boolean isAlreadyReported = AlreadyReported.get(UUID);

            //checks for already scammer status
            if (isScammerInDatabase) {
                report.reply("Sorry, but this person is already a scammer.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                return;
            }

            if (isAlreadyReported) {
                report.reply("Sorry, but this person has been already reported.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                return;
            }

            //gets stolen/scammed for and video proof
            String IGN = com.udu3324.api.IGN.find(checkForIGN);
            int secondArgumentSeparator = input.indexOf(" - ", firstArgumentSeparator + 3);
            String scammedFor = input.substring(firstArgumentSeparator + 3, secondArgumentSeparator);
            String videoLink = input.substring(secondArgumentSeparator + 3);

            EmbedBuilder eb2 = new EmbedBuilder();
            eb2.setAuthor(IGN + " (report waiting to be confirmed)", null, null);
            eb2.setDescription("[" + UUID + "](https://namemc.com/profile/" + checkForIGN + ")");
            eb2.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            eb2.addField("Scammed For", scammedFor, false);
            eb2.addField("Proof", videoLink, false);
            eb2.addField("Reported By", "<@" + report.getAuthor().getId() + ">", false);
            eb2.setColor(new Color(0x181818));
            eb2.setFooter(Config.prefix + "report [ign/uuid] - [scammed items] - [vid url]");

            //this is optional and is to get players opinions
            report.reply(eb2.build()).queue(message -> {
                message.addReaction("\u2b06").queue();
                message.addReaction("\u2b07").queue();
            });

            CreateReport.create(UUID, scammedFor, videoLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
