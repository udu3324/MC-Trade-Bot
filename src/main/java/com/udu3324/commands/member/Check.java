package com.udu3324.commands.member;

import com.udu3324.main.Config;
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
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Wrong IGN!");
        eb.setDescription("You didn't type the ign/uuid correctly! Fix your typo. \n" +
                "```" +
                Config.prefix + "check [insert ign/uuid here]" +
                "```\n" +
                "**Examples That Would Work** \n" +
                Config.prefix + "check Notch \n" +
                Config.prefix + "check 069a79f4-44e9-4726-a5be-fca90e38aaf5 \n" +
                Config.prefix + "check 069a79f444e94726a5befca90e38aaf5 ");
        eb.setColor(new Color(0xFFC400));

        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        MessageChannel channel = event.getChannel();
        Message check = event.getMessage();
        String input = check.getContentRaw();

        //disable chatting in the check channel
        if (!input.contains(Config.prefix + "check") || channel.getId().equals(Config.reportChannelID) || input.length() <= 6) {
            if (channel.getId().equals(Config.checkChannelID) && !event.getAuthor().isBot() && !isStaffMember && !input.contains(Config.prefix + "delete")) {
                channel.sendMessage("Hey! You aren't allowed to talk here unless if you're checking for a scammer.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                channel.sendMessage(eb.setTitle("Use this channel correctly!").build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                check.delete().queueAfter(500, TimeUnit.MILLISECONDS);
            }
            return;
        }

        long waitTime = RandomInt.get(3000, 6000);
        String input2 = input.substring(7);

        // wait animation to slow down people
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
                    String ConfirmCorrect = com.udu3324.api.IGN.find(input2);
                    if (ConfirmCorrect.equals("Not a IGN or UUID!")) {
                        check.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                        check.delete().queue();
                        return;
                    }

                    String IGN = com.udu3324.api.IGN.find(input2);
                    String UUID = com.udu3324.api.UUID.find(input2);

                    //they have been reported + it was accepted
                    if (ScammerStatusDatabase.get(UUID)) {
                        String[] str = ScammerInfo.get(UUID);

                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setAuthor(IGN, null, null);
                        eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\nThis person is a scammer! Reports were found. Don't trade with them unless you know what you're doing!");
                        eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
                        eb.addField("Scammed For", str[0], false);
                        eb.addField("Proof", str[1], false);
                        eb.addField("Past Usernames", "You can view them [by clicking here.](https://namemc.com/profile/" + IGN + ")", false);
                        eb.setColor(new Color(0xBB2F2D));
                        eb.setFooter(Config.prefix + "check [ign/uuid]");
                        check.reply(eb.build()).queue();
                        return;
                    }

                    //they have been reported
                    if (AlreadyReported.get(UUID)) {
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
                        eb.setFooter(Config.prefix + "check [ign/uuid]");
                        check.reply(eb.build()).queue();
                        return;
                    }

                    //they are not a scammer
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(IGN, null, null);
                    eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN + ")\n" + "This person isn't a scammer. No reports were found. You don't have to worry that much.");
                    eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
                    eb.setColor(new Color(0xA1DC5C));
                    eb.setFooter(Config.prefix + "check [ign/uuid]");
                    check.reply(eb.build()).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(task, waitTime);
    }
}
