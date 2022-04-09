package com.udu3324.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent ev) {
        JDA jda = ev.getJDA();

        System.out.println("Operating in " + jda.getGuilds().get(0).getName());

        // file check/create
        File unconfirmed = new File("unconfirmed.txt");
        File confirmed = new File("confirmed.txt");

        try {
            //check if confirmed.txt exists
            if (confirmed.createNewFile()) {
                System.out.println("(okay/bad) File created: " + confirmed.getName() +
                        "\n A text file was created to store confirmed reports." +
                        "\n If you had previous confirmed reports, replace confirmed.txt with your old confirmed reports file");
            } else {
                System.out.println("(good) File already exists: " + confirmed.getName());
            }

            //check if unconfirmed.txt exists
            if (unconfirmed.createNewFile()) {
                System.out.println("(okay/bad) File created: " + unconfirmed.getName() +
                        "\n A text file was created to store unconfirmed reports." +
                        "\n If you had previous confirmed reports, replace unconfirmed.txt with your old unconfirmed reports file");
            } else {
                System.out.println("(good) File already exists: " + unconfirmed.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
