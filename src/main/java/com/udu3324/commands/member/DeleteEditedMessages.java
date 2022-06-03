package com.udu3324.commands.member;

import com.udu3324.main.Data;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class DeleteEditedMessages extends ListenerAdapter {
    public void onMessageUpdate(MessageUpdateEvent event) {
        MessageChannel channel = event.getChannel();
        if (channel.getIdLong() == Data.checkChannelID && !event.getAuthor().isBot()) {
            User user = event.getAuthor();
            event.getMessage().delete().queue();
            channel.sendMessage("Hey <@" + user.getId() + ">, please do not attempt to talk in this channel. It is" +
                    " for checking scammers only.").queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
        }
    }
}
