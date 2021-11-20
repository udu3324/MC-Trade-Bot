package com.udu3324.commands.member;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.udu3324.api.IGNHistory;
import com.udu3324.main.Data;
import com.udu3324.tasks.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Check extends ListenerAdapter {
    final SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMMMMMM dd, yyyy");

    public void onMessageReceived(MessageReceivedEvent event) {
        boolean isStaffMember = StaffCheck.isStaffMember(event.getMember());

        MessageChannel channel = event.getChannel();
        Message check = event.getMessage();
        if (check.getContentRaw().contains(Data.command + "check") && !(check.getChannel() == Data.report) && !(check.getContentRaw().length() <= 6)) {
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
                                    Data.command + "check NintendoOS \n" +
                                    Data.command + "check edd3eaa1-31db-4faf-903e-fbcfd3b501d3 \n" +
                                    Data.command + "check edd3eaa131db4faf903efbcfd3b501d3");
                            eb.setColor(new Color(0xFFC400));
                            check.reply(eb.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                            check.delete().queue();
                        } else {
                            String IGN2 = com.udu3324.api.IGN.find(input);
                            String UUID = com.udu3324.api.UUID.find(input);

                            sendCheck(ScammerStatusDatabase.get(UUID), ScammerStatusMWDiscord.get(UUID), UUID, IGN2, input, check);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(task, waitTime);
        } else if (check.getChannel() == Data.check && !event.getAuthor().isBot() && !isStaffMember) {
            channel.sendMessage("Hey! You aren't allowed to talk here unless if you're checking for a scammer.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            EmbedBuilder eb3 = new EmbedBuilder();
            eb3.setTitle("Use this channel correctly!");
            eb3.setDescription("You didn't type the ign/uuid correctly! Fix your typo. \n" +
                    "```" +
                    Data.command + "check [insert ign/uuid here]" +
                    "```\n" +
                    "**Examples That Would Work** \n" +
                    Data.command + "check NintendoOS \n" +
                    Data.command + "check edd3eaa1-31db-4faf-903e-fbcfd3b501d3 \n" +
                    Data.command + "check edd3eaa131db4faf903efbcfd3b501d3");
            channel.sendMessage(eb3.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            check.delete().queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

    public void sendCheck(boolean isScammerInDatabase, boolean isScammerInMWDiscord, String UUID, String IGN2, String input, Message check) throws Exception {
        if (isScammerInDatabase || isScammerInMWDiscord) {
            String[] str = ScammerInfo.get(UUID);
            JsonArray array = IGNHistory.find(input);
            int index = array.size() - 1;
            StringBuilder pastIGNField = new StringBuilder();
            do {
                JsonElement currentArray = array.get(index);
                String arrStr = currentArray.toString();
                int nameIndex = arrStr.indexOf("\"name\":\"") + 8;
                int nameIndex2 = arrStr.indexOf("\",\"");
                if (nameIndex2 <= -1) { //code below is to stop loop before last ign (last ign does not cache date)
                    nameIndex2 = arrStr.indexOf("\"}");
                    String ign = arrStr.substring(nameIndex, nameIndex2); //ign
                    pastIGNField.append(ign);
                    break;
                }
                String ign = arrStr.substring(nameIndex, nameIndex2); //ign

                String time = arrStr.substring(arrStr.indexOf("\"changedToAt\":") + 14, arrStr.indexOf("}")); //time
                Date date = new Date(Long.parseLong(time));
                String formattedDate = sdf.format(date);
                pastIGNField.append(ign).append(" - ").append(formattedDate).append("\n");
                index--;
            } while (index >= 0);

            String description;
            if (isScammerInDatabase && isScammerInMWDiscord) {
                description = "This person is a scammer! Reports were found from the Database and the MW Discord. Don't trade with them unless you know what you're doing!";
            } else if (isScammerInDatabase) {
                description = "This person is a scammer! Reports were found from the Database. Don't trade with them unless you know what you're doing!";
            } else {
                description = "This person is a scammer! Reports were found from the MW Discord. Don't trade with them unless you know what you're doing!";
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(IGN2, null, null);
            eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN2 + ")\n" + description);
            eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            if (isScammerInDatabase) {
                eb.addField("Scammed For", str[0], false);
                eb.addField("Proof", str[1], false);
            }
            eb.addField("Past Usernames", FixDiscordSyntax.fix(String.valueOf(pastIGNField)), false);
            eb.setColor(new Color(0xBB2F2D));
            eb.setFooter("To check for another scammer, do \"" + Data.command + "check [insert ign/uuid]\" again.");
            check.reply(eb.build()).queue();
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(IGN2, null, null);
            eb.setDescription("[" + UUID + "](https://namemc.com/profile/" + IGN2 + ")\n" + "This person isn't a scammer. No reports were found from the Database or MW Discord. You don't have to worry that much.");
            eb.setThumbnail("https://crafatar.com/renders/body/" + UUID + "?overlay");
            eb.setColor(new Color(0xA1DC5C));
            eb.setFooter("To check for another scammer, do \"" + Data.command + "check [insert ign/uuid]\" again.");
            check.reply(eb.build()).queue();
        }
    }
}
