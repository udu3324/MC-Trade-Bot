package com.udu3324.main;

import com.udu3324.commands.member.*;
import com.udu3324.commands.staff.Accept;
import com.udu3324.commands.staff.Reject;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class MainBot {
    public static void main(String[] args) throws LoginException {
        // Discord Bot Builder
        System.out.println("+------------------------------------------+\n" +
                "|        MC Trade Bot is by udu3324        |\n" +
                "+------------------------------------------+\n" +
                "| Hey! Thank you for looking at my code.   |\n" +
                "| Check more of my projects on Github!     |\n" +
                "| -----> https://github.com/udu3324 <----- |\n" +
                "|                                          |\n" +
                "| MC Trade Bot is a QOL tool to report     |\n" +
                "| and check for MC item scammers in        |\n" +
                "| Discord.                                 |\n" +
                "+------------------------------------------+");
        JDABuilder
                .createLight(Data.token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new ReadyListener(), new DeleteEditedMessages(), new Ping(), new Help(), new Check(), new Report(), new Accept(), new Reject())
                .build();
    }
}
