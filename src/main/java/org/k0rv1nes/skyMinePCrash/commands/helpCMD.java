package org.k0rv1nes.skyMinePCrash.commands;


import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.ChatColor;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;

import java.util.List;

public class helpCMD {

    public void HelpCMD(){
        new CommandAPICommand("pcrash")
                .withSubcommand(new CommandAPICommand("help"))
                    .executes((sender, args) -> {
                        if(sender.hasPermission("pcrash.help") || sender.isOp() ){
                            List<String> messages = SkyMinePCrash
                                    .getInstance()
                                    .getConfig()
                                    .getStringList("messages.help");
                            for (String message : messages) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    SkyMinePCrash.getInstance()
                                            .getConfig()
                                            .getString("messages.noPermission")));
                        }

                    }).register();
    }

}
