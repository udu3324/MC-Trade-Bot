package com.udu3324.commands.member;

import com.udu3324.main.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Help extends ListenerAdapter {
    //help command
    public void onMessageReceived(MessageReceivedEvent event) {
        Message help = event.getMessage();

        if (help.getContentRaw().equals(Config.prefix + "help")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Help");
            eb.setDescription("MC Trade Bot is a bot that reports and handles reports of scammers on MC.");
            eb.addField("Prefix", "The current prefix set is **" + Config.prefix + "**", false);

            eb.addField("Member Commands",
                    Config.prefix + "**help** - shows help embed\n" +
                            Config.prefix + "**check [player-ign/uuid]** - checks if they are a scammer\n" +
                            Config.prefix + "**report [player-ign/uuid] - [what they stole] - [youtube link]** - creates a report\n" +
                            Config.prefix + "**ping** - shows time delay between you and the bot", false);

            eb.addField("Staff Commands",
                    Config.prefix + "**accept [ign/uuid]** - accepts the scammer report\n" +
                            Config.prefix + "**reject [ign/uuid]** - rejects the scammer report\n" +
                            Config.prefix + "**delete [ign/uuid]** - deletes a confirmed scammer report", false);

            eb.setColor(new Color(0x259FF5));
            help.reply(eb.build()).queue();
        }
    }
}
