package com.udu3324.commands.member;

import com.udu3324.main.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Help extends ListenerAdapter {
    //help command
    public void onMessageReceived(MessageReceivedEvent event) {
        Message help = event.getMessage();

        if (help.getContentRaw().equals(Data.command + "help")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Help");
            eb.setDescription("Minewind Scammer Bot is a bot that reports and handles reports of scammers.");
            eb.addField("Prefix", "The current prefix set is **" + Data.command + "**", false);

            eb.addField("Member Commands",
                    Data.command + "**help** - shows help embed\n" +
                            Data.command + "**check [player-ign/uuid]** - checks if they are a scammer\n" +
                            Data.command + "**report [player-ign/uuid] - [what they stole] - [youtube link]** - creates a report\n" +
                            Data.command + "**ping** - shows time delay between you and the bot\n" +
                            Data.command + "**credits** - shows credits of the bot and the server", false);

            eb.addField("Staff Commands",
                    Data.command + "**accept [ign/uuid]** - accepts the scammer report\n" +
                            Data.command + "**reject [ign/uuid]** - rejects the scammer report", false);

            eb.setColor(new Color(0x259FF5));
            help.reply(eb.build()).queue();
        }
    }
}
