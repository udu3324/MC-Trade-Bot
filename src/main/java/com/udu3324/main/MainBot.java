package com.udu3324.main;

import com.udu3324.commands.member.*;
import com.udu3324.commands.staff.Accept;
import com.udu3324.commands.staff.Info;
import com.udu3324.commands.staff.Reject;
import com.udu3324.commands.staff.Rules;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.File;

public class MainBot {
    public static void main(String[] args) throws LoginException {
        // Discord Bot Builder
        JDABuilder
                .createLight(Token.token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Credits(), new Ping(), new Help(), new Check(), new Data(), new Report(), new Accept(), new Reject(), new Info(), new Rules())
                .setActivity(Activity.playing(Data.activity))
                .build();

        File file = new File("data");
        if (!file.exists()) {
            boolean result = file.mkdir();
            if (result) {
                System.out.println("Successfully created " + file.getAbsolutePath());
            } else {
                System.out.println("Failed creating " + file.getAbsolutePath());
            }
        } else {
            System.out.println("\"data\" directory exists.");
        }

        File file2 = new File("unconfirmed");
        if (!file2.exists()) {
            boolean result = file2.mkdir();
            if (result) {
                System.out.println("Successfully created " + file2.getAbsolutePath());
            } else {
                System.out.println("Failed creating " + file2.getAbsolutePath());
            }
        } else {
            System.out.println("\"unconfirmed\" directory exists.");
        }
    }
}
