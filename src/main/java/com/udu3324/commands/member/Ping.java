package com.udu3324.commands.member;

import com.udu3324.main.Data;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message ping = event.getMessage();
        if (ping.getContentRaw().equals(Data.command + "ping")) {
            if (ping.isFromType(ChannelType.TEXT)) {
                MessageChannel channel = event.getChannel();
                long time = System.currentTimeMillis();
                channel.sendMessage("Pong!")
                        .queue(response -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue());
            }
        }
    }
}
