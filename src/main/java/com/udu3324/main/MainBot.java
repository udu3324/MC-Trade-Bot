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
import java.io.IOException;

public class MainBot {
    public static void main(String[] args) throws LoginException {
        // Discord Bot Builder
        JDABuilder
                .createLight(Token.token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Credits(), new Ping(), new Help(), new Check(), new Data(), new Report(), new Accept(), new Reject(), new Info(), new Rules())
                .setActivity(Activity.playing(Data.activity))
                .build();

        File confirmed = new File("confirmed.txt");
        try {
            if (confirmed.createNewFile()) {
                System.out.println("File created: " + confirmed.getName());
            } else {
                System.out.println("File already exists: " + confirmed.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File unconfirmed = new File("unconfirmed.txt");
        try {
            if (unconfirmed.createNewFile()) {
                System.out.println("File created: " + unconfirmed.getName());
            } else {
                System.out.println("File already exists: " + unconfirmed.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
