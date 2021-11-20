package com.udu3324.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent ev) {
        JDA jda = ev.getJDA();

        //set channels
        Guild guild = jda.getGuildById(Data.guildID);
        assert guild != null;
        Data.report = guild.getTextChannelById(Data.reportChannelID);
        Data.check = guild.getTextChannelById(Data.checkChannelID);

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
