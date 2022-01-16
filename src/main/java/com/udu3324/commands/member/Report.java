package com.udu3324.commands.member;

import com.udu3324.main.Data;
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
    public static void createReport(Message report, String input, String checkForIGN, int firstArgumentSeparator, String UUID) throws Exception {
        //gets userID
        String temp = String.valueOf(report.getAuthor());
        int substring1 = String.valueOf(report.getAuthor()).indexOf("(") + 1;
        int substring2 = String.valueOf(report.getAuthor()).length() - 1;
        String userID = temp.substring(substring1, substring2);
        //gets stolen/scammed for and video proof
        String IGN = com.udu3324.api.IGN.find(checkForIGN);
        int secondArgumentSeparator = input.indexOf(" - ", firstArgumentSeparator + 3);
        String scammedFor = input.substring(firstArgumentSeparator + 3, secondArgumentSeparator);
        String videoLink = input.substring(secondArgumentSeparator + 3);

        EmbedBuilder eb2 = new EmbedBuilder();
        eb2.setAuthor(IGN + " (report waiting to be confirmed)", null, null);
        eb2.setDescription("[" + UUID + "](https://namemc.com/profile/" + checkForIGN + ")\n" + "This person is a **REPORTED** scammer! This report has not been confirmed yet.");
        eb2.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
        eb2.addField("Scammed For", scammedFor, false);
        eb2.addField("Proof", videoLink, false);
        eb2.addField("Reported By", "<@" + userID + ">", false);
        eb2.setColor(new Color(0x181818));
        eb2.setFooter(Data.command + "report [ign/uuid] - [scammed items] - [vid url]");
        //this is optional and is to get players opinions
        report.reply(eb2.build()).queue(message -> {
            message.addReaction("\u2b06").queue();
            message.addReaction("\u2b07").queue();
        });
        CreateReport.create(UUID, scammedFor, videoLink);
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        EmbedBuilder syntaxInvalid = new EmbedBuilder();
        syntaxInvalid.setTitle("Wrong Syntax!");
        syntaxInvalid.setDescription("You did not use the command correct! You probably had a typo. \n" +
                "```" +
                Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Data.command + "report Notch - dirt - https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" +
                Data.command + "report 069a79f4-44e9-4726-a5be-fca90e38aaf5 - golden apples - https://www.youtube.com/watch?v=D1qU745zMKU \n" +
                Data.command + "report 069a79f444e94726a5befca90e38aaf5 - diamond block - https://www.youtube.com/watch?v=zYUvgxwuJbA ");
        syntaxInvalid.setColor(new Color(0x6F2EBB));

        EmbedBuilder contextInvalid = new EmbedBuilder();
        contextInvalid.setTitle("Use this channel correctly!");
        contextInvalid.setDescription("This channel is for only reporting scammers. Look below for more info on how to report a scammer.\n" +
                "```" +
                Data.command + "report [player-ign/uuid] - [what they scammed] - [youtube link]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Data.command + "report Notch - dirt - https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" +
                Data.command + "report 069a79f4-44e9-4726-a5be-fca90e38aaf5 - golden apples - https://www.youtube.com/watch?v=D1qU745zMKU \n" +
                Data.command + "report 069a79f444e94726a5befca90e38aaf5 - diamond block - https://www.youtube.com/watch?v=zYUvgxwuJbA ");

        Message report = event.getMessage();
        MessageChannel channel = event.getChannel();
        if (report.getContentRaw().contains(Data.command + "report")) {
            if (channel.getIdLong() == Data.reportChannelID) {
                //filter out messages with only report and nothing else in it
                if (report.getContentRaw().equals(Data.command + "report")) {
                    report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                    report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                } else if (report.getContentRaw().contains(Data.command + "report ")) {
                    try {
                        String input = report.getContentRaw();

                        int firstArgumentSeparator = input.indexOf(" - ");
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
                            String ignOrUUID = input.substring(8, firstArgumentSeparator);
                            String checkForIGN = com.udu3324.api.IGN.find(ignOrUUID);

                            //filter out reports with incorrect IGNs
                            if (checkForIGN.equals("Not a IGN or UUID!")) {
                                report.reply("The IGN/UUID is wrong! Check for typos.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                            } else {
                                String UUID = com.udu3324.api.UUID.find(checkForIGN);
                                //a lot of boolean checks
                                boolean isScammerInDatabase = ScammerStatusDatabase.get(UUID);
                                boolean isScammerInMWDiscord = ScammerStatusDatabase.get(UUID);
                                boolean isAlreadyReported = AlreadyReported.get(UUID);
                                //checks for already scammer status
                                if (isScammerInDatabase || isScammerInMWDiscord) {
                                    report.reply("Sorry, but this person is already a scammer.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                    report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                                } else {
                                    //checks for already reported status
                                    if (isAlreadyReported) {
                                        report.reply("Sorry, but this person has been already reported.").queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                                        report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                                    } else {
                                        createReport(report, input, checkForIGN, firstArgumentSeparator, UUID);
                                    }
                                }
                            }
                        } else {
                            report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                            report.delete().queueAfter(3000, TimeUnit.MILLISECONDS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    report.reply(syntaxInvalid.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
                }
            } else {
                channel.sendMessage("Hey! You can only use that command in <#" + Data.reportChannelID + ">. ").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                report.delete().queueAfter(3, TimeUnit.SECONDS);
            }
        } else if (channel.getIdLong() == Data.reportChannelID && !event.getAuthor().isBot() && !isStaffMember && !report.getContentRaw().contains(Data.command + "reject") && !report.getContentRaw().contains(Data.command + "accept") && !(event.getAuthor().getIdLong() == Data.maintainerID)) {
            channel.sendMessage("Hey! You aren't allowed to talk here unless if you're submitting a scammer report.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            channel.sendMessage(contextInvalid.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            report.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }
}
