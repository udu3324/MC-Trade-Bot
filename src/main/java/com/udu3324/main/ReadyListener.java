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

        // file check
        File unconfirmed = new File("unconfirmed.txt");
        File confirmed = new File("confirmed.txt");
        try {
            //check if confirmed.txt exists
            if (confirmed.createNewFile()) {
                if (Data.mwMode) { //print error
                    System.out.println("(okay/bad) File created: " + confirmed.getName() + " | if you are starting off with " +
                            "no confirmed reports, then that's fine. If you want the original database with all the confirmed " +
                            "reports, msg _._#3324 via discord.");
                } else {
                    System.out.println("(okay/bad) File created: " + confirmed.getName() + " | if you are starting off with " +
                            "no confirmed reports, then that's fine. if you have a previous confirmed.txt, you should replace " +
                            "the newly created confirmed.txt");
                }
            } else {
                System.out.println("(good) File already exists: " + confirmed.getName());
            }

            //check if unconfirmed.txt exists
            if (unconfirmed.createNewFile()) {
                System.out.println("(okay) File created: " + unconfirmed.getName());
            } else {
                System.out.println("(good) File already exists: " + unconfirmed.getName());
            }

            // check for mwdiscord.txt if mw mode is activated
            if (Data.mwMode) {
                File mwdiscord = new File("mwdiscord.txt");
                if (mwdiscord.createNewFile()) {
                    System.out.println("(okay/bad) File created: " + mwdiscord.getName() + " | if you are starting off with " +
                            "no confirmed reports, then that's fine. If you want the original mw discord reports, msg _._#3324 " +
                            "via discord.");
                } else {
                    System.out.println("(good) File already exists: " + mwdiscord.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
