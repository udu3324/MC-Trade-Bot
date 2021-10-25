package com.udu3324.main;

import com.udu3324.commands.member.*;
import com.udu3324.commands.staff.Accept;
import com.udu3324.commands.staff.Reject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class MainBot {
    public static JDA jda;
    public static JDA getJda() {
        return jda;
    }

    public static void main(String[] args) throws LoginException {
        // Discord Bot Builder
        System.out.println("+-------------------------------------------+\n" +
                           "|       MW Scammer Bot is by udu3324        |\n" +
                           "+-------------------------------------------+\n" +
                           "| Hey! Thank you for looking at my code.    |\n" +
                           "| Check out more of my projects on Github!  |\n" +
                           "| -----> https://github.com/udu3324 <-----  |\n" +
                           "|                                           |\n" +
                           "| MW Scammer Bot is a QOL tool for traders  |\n" +
                           "| on MW. You can easily report scammers and |\n" +
                           "| check for their status.                   |\n" +
                           "+-------------------------------------------+");
        jda = JDABuilder
                .createLight(Token.token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Credits(), new Ping(), new Help(), new Check(), new Report(), new Accept(), new Reject())
                .setActivity(Activity.playing(Data.activity))
                .build();

        //checks if mwdiscord.txt exists
        File mwdiscord = new File("mwdiscord.txt");
        try {
            if (mwdiscord.createNewFile()) {
                System.out.println("(okay/bad) File created: " + mwdiscord.getName() + " | if you are starting off with " +
                        "no confirmed reports, then that's fine. If you want the original mw discord reports, msg _._#3324 " +
                        "via discord.");
            } else {
                System.out.println("(good) File already exists: " + mwdiscord.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred. This is very bad. Make sure the program has permission to write/read .txt files.");
            e.printStackTrace();
        }

        //checks if confirmed.txt exists
        File confirmed = new File("confirmed.txt");
        try {
            if (confirmed.createNewFile()) {
                System.out.println("(okay/bad) File created: " + confirmed.getName() + " | if you are starting off with " +
                        "no confirmed reports, then that's fine. If you want the original database with all the confirmed " +
                        "reports, msg _._#3324 via discord.");
            } else {
                System.out.println("(good) File already exists: " + confirmed.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred. This is very bad. Make sure the program has permission to write/read .txt files.");
            e.printStackTrace();
        }

        //checks if unconfirmed.txt exists
        File unconfirmed = new File("unconfirmed.txt");
        try {
            if (unconfirmed.createNewFile()) {
                System.out.println("(okay) File created: " + unconfirmed.getName());
            } else {
                System.out.println("(good) File already exists: " + unconfirmed.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred. This is very bad. Make sure the program has permission to write/read .txt files.");
            e.printStackTrace();
        }
    }
}
