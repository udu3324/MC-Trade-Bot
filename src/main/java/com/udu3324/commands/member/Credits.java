package com.udu3324.commands.member;

import com.udu3324.main.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Credits extends ListenerAdapter {
    //credits command
    public void onMessageReceived(MessageReceivedEvent event) {
        Message credits = event.getMessage();

        if (credits.getContentRaw().equals(Data.command + "credits")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Credits");
            eb.setDescription("<@395649963415306242> - Made literally everything \n" +
                    "<@217806943765528577> - Helped me with the bot n stuff \n" +
                    "<@252257930122887168> - Made the logo \n" +
                    "<@323220775194853377> - Beta tester and sent good feedback");
            eb.setColor(new Color(0x33CC43));
            eb.setAuthor("MW Scammer Bot");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(eb.build()).queue();
        }
    }
}
