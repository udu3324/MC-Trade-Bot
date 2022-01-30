package com.udu3324.commands.member;

import com.udu3324.main.Data;
import com.udu3324.tasks.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Check extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        MessageChannel channel = event.getChannel();
        Message check = event.getMessage();
        if (check.getContentRaw().contains(Data.command + "check") && !(channel.getIdLong() == Data.reportChannelID) && !(check.getContentRaw().length() <= 6)) {
            long waitTime = RandomInt.get(3000, 6000);
            String input = check.getContentRaw().substring(7);

            channel.sendMessage("`[---] Searching Database`").queue(message -> {
                message.editMessage("`[#--] Searching Database`").queueAfter(waitTime / 4, TimeUnit.MILLISECONDS);
                message.editMessage("`[##-] Searching Database`").queueAfter(waitTime / 4 + waitTime / 4, TimeUnit.MILLISECONDS);
                message.editMessage("`[###] Searching Database`").queueAfter(waitTime / 4 + waitTime / 4 + waitTime / 4, TimeUnit.MILLISECONDS);
                message.delete().queueAfter(waitTime, TimeUnit.MILLISECONDS);
            });

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        String ConfirmCorrect = com.udu3324.api.IGN.find(input);
                        if (ConfirmCorrect.equals("Not a IGN or UUID!")) {
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setTitle("Wrong IGN!");
                            eb.setDescription("You didn't type the ign/uuid correctly! Fix your typo. \n" +
                                    "```" +
                                    Data.command + "check [insert ign/uuid here]" +
                                    "```\n" +
                                    "**Examples That Would Work** \n" +
                                    Data.command + "check Notch \n" +
                                    Data.command + "check 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                                    Data.command + "check 069a79f444e94726a5befca90e38aaf5 ");
                            eb.setColor(new Color(0xFFC400));
                            check.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                            check.delete().queue();
                        } else {
                            String IGN = com.udu3324.api.IGN.find(input);
                            String UUID = com.udu3324.api.UUID.find(input);

                            boolean mwDiscord;
                            if (Data.mwMode) {
                                mwDiscord = ScammerStatusMWDiscord.get(UUID);
                            } else {
                                mwDiscord = false;
                            }

                            sendCheck(ScammerStatusDatabase.get(UUID), mwDiscord, UUID, IGN, check);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(task, waitTime);
        } else if (channel.getIdLong() == Data.checkChannelID && !event.getAuthor().isBot() && !isStaffMember && !(event.getAuthor().getIdLong() == Data.maintainerID)) {
            channel.sendMessage("Hey! You aren't allowed to talk here unless if you're checking for a scammer.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            EmbedBuilder eb3 = new EmbedBuilder();
            eb3.setTitle("Use this channel correctly!");
            eb3.setDescription("You didn't type the ign/uuid correctly! Fix your typo. \n" +
                    "```" +
                    Data.command + "check [insert ign/uuid here]" +
                    "```\n" +
                    "**Examples That Would Work** \n" +
                    Data.command + "check Notch \n" +
                    Data.command + "check 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                    Data.command + "check 069a79f444e94726a5befca90e38aaf5 ");
            channel.sendMessage(eb3.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            check.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

    public void sendCheck(boolean isScammerInDatabase, boolean isScammerInMWDiscord, String UUID, String IGN, Message msgReplyTo) throws Exception {
        boolean isReportedButNotConfirmed = AlreadyReported.get(UUID);
        if (isScammerInDatabase || isScammerInMWDiscord) {
            String[] str = ScammerInfo.get(UUID);

            String description;
            if (isScammerInDatabase && isScammerInMWDiscord) {
                description = "This person is a scammer! Reports were found from the Database and the MW Discord. Don't trade with them unless you know what you're doing!";
            } else if (isScammerInDatabase) {
                description = "This person is a scammer! Reports were found from the Database. Don't trade with them unless you know what you're doing!";
            } else {
                description = "This person is a scammer! Reports were found from the MW Discord. Don't trade with them unless you know what you're doing!";
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(IGN, null, null);
            eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\n" + description);
            eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            if (isScammerInDatabase) {
                eb.addField("Scammed For", str[0], false);
                eb.addField("Proof", str[1], false);
            }
            eb.addField("Past Usernames", "You can view them [by clicking here.](https://namemc.com/profile/" + IGN + ")", false);
            eb.setColor(new Color(0xBB2F2D));
            eb.setFooter(Data.command + "check [ign/uuid]");
            msgReplyTo.reply(eb.build()).queue();
        } else if (isReportedButNotConfirmed) {
            String[] str = UnconfirmedReport.get(UUID);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(IGN, null, null);
            eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\n" + "This person is reported " +
                    "but the **report hasn't been accepted or rejected** yet. Trade with this person with caution. \n\n" +
                    "If you feel like staff need to take action of this report, tell them.");
            eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            eb.addField("Scammed For", str[0], false);
            eb.addField("Proof", str[1], false);
            eb.addField("Past Usernames", "You can view them [by clicking here.](https://namemc.com/profile/" + IGN + ")", false);
            eb.setColor(new Color(0xede31a));
            eb.setFooter(Data.command + "check [ign/uuid]");
            msgReplyTo.reply(eb.build()).queue();
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(IGN, null, null);
            if (Data.mwMode) {
                eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\n" + "This person isn't a scammer. No reports were found from the Database or MW Discord. You don't have to worry that much.");
            } else {
                eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\n" + "This person isn't a scammer. No reports were found from the Database. You don't have to worry that much.");
            }
            eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            eb.setColor(new Color(0xA1DC5C));
            eb.setFooter(Data.command + "check [ign/uuid]");
            msgReplyTo.reply(eb.build()).queue();
        }
    }
}
