package com.udu3324.commands.member;

import com.udu3324.main.Data;
import com.udu3324.tasks.AlreadyReported;
import com.udu3324.tasks.CreateScammerInfo;
import com.udu3324.tasks.ScammerStatus;
import com.udu3324.tasks.StaffCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Report extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong Syntax!");
        eb.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Data.command + "report NintendoOS - dyrn - https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" +
                Data.command + "report edd3eaa1-31db-4faf-903e-fbcfd3b501d3 - emp - https://www.youtube.com/watch?v=D1qU745zMKU \n" +
                Data.command + "report edd3eaa131db4faf903efbcfd3b501d3 - deez nuts - https://www.youtube.com/watch?v=zYUvgxwuJbA ");
        eb.setColor(new Color(0x6F2EBB));
        Message report = event.getMessage();
        MessageChannel channel = event.getChannel();
        if (report.getContentRaw().contains(Data.command + "report")) {
            if (report.isFromType(ChannelType.TEXT)) {
                if (report.getChannel() == Data.report) {

                    if (report.getContentRaw().equals(Data.command + "report")) {
                        report.reply(eb.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                        report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                    } else if (report.getContentRaw().contains(Data.command + "report ")) {
                        try {
                            String input = report.getContentRaw();

                            int firstVerticalBar = input.indexOf(" - ");
                            int lastIndex = 0;
                            int count = 0;
                            while (lastIndex != -1) {
                                lastIndex = input.indexOf(" - ", lastIndex);
                                if (lastIndex != -1) {
                                    count++;
                                    lastIndex += " - ".length();
                                }
                            }
                            if (count == 2) {
                                String ignOrUUID = input.substring(8, firstVerticalBar);
                                String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);
                                if (checkForIGN.equals("Not a IGN or UUID!")) {
                                    report.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                    report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                                } else {
                                    String UUID = com.udu3324.api.UUID.find(checkForIGN);
                                    boolean isScammer = ScammerStatus.get(UUID);
                                    boolean isAlreadyReported = AlreadyReported.get(UUID);
                                    if (isScammer) {
                                        report.reply("Sorry, but this person is already a scammer.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                        report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                                    } else {
                                        if (isAlreadyReported) {
                                            report.reply("Sorry, but this person has been already reported.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                            report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                                        } else {
                                            String temp = String.valueOf(report.getAuthor());
                                            int substring1 = String.valueOf(report.getAuthor()).indexOf("(") + 1;
                                            int substring2 = String.valueOf(report.getAuthor()).length() - 1;
                                            String userID = temp.substring(substring1, substring2);

                                            String IGN = com.udu3324.api.IGN.find(checkForIGN);
                                            int secondVerticalBar = input.indexOf(" - ", firstVerticalBar + 3);
                                            String scammedFor = input.substring(firstVerticalBar + 3, secondVerticalBar);
                                            String videoLink = input.substring(secondVerticalBar + 3);

                                            EmbedBuilder eb2 = new EmbedBuilder();
                                            eb2.setAuthor(IGN + " (report is in progress of being confirmed)", null, null);
                                            eb2.setDescription("[" + UUID + "](https://namemc.com/profile/" + checkForIGN + ")\n" + "This person is a **REPORTED** scammer! (doesn't mean that it's confirmed scammer) Don't trade with them unless you know what you're doing!");
                                            eb2.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
                                            eb2.addField("Scammed For", scammedFor, false);
                                            eb2.addField("Proof", videoLink, false);
                                            eb2.addField("Reported By", "<@" + userID + ">", false);
                                            eb2.setColor(new Color(0xFFFFFF));
                                            eb2.setFooter("To report another scammer, do \"" + Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]\" again.");
                                            report.reply(eb2.build()).queue(message -> {
                                                message.addReaction("\u2705").queue();
                                                message.addReaction("\u274E").queue();
                                            });
                                            CreateScammerInfo.create(UUID, scammedFor, videoLink);
                                        }
                                    }
                                }
                            } else {
                                report.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                report.delete().queueAfter(3000, TimeUnit.MILLISECONDS);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        report.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                        report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                    }

                } else {
                    channel.sendMessage("Hey! You can only use that command in <#" + Data.reportChannelID + ">.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    report.delete().queueAfter(3, TimeUnit.SECONDS);
                }
            } else {
                report.reply("Hey! You can only use that command in <#" + Data.reportChannelID + ">.").queue();
            }
        } else if (report.getChannel() == Data.report && !event.getAuthor().isBot() && !isStaffMember && !report.getContentRaw().contains(Data.command + "reject") && !report.getContentRaw().contains(Data.command + "accept")) {
            channel.sendMessage("Hey! You aren't allowed to talk here unless if you're submitting a scammer report.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            EmbedBuilder eb3 = new EmbedBuilder();
            eb3.setTitle("Use this channel correctly!");
            eb3.setDescription("This channel is for only reporting scammers. Look below for more info on how to report a scammer.\n" +
                    "```" +
                    Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]" +
                    "```\n" +
                    "**Examples That Would Work** \n" +
                    Data.command + "report NintendoOS - dyrn - https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" +
                    Data.command + "report edd3eaa1-31db-4faf-903e-fbcfd3b501d3 - emp - https://www.youtube.com/watch?v=D1qU745zMKU \n" +
                    Data.command + "report edd3eaa131db4faf903efbcfd3b501d3 - deez nuts - https://www.youtube.com/watch?v=zYUvgxwuJbA ");
            channel.sendMessage(eb3.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }

    }
}
