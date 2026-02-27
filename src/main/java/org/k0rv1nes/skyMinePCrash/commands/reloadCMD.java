package org.k0rv1nes.skyMinePCrash.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;

public class reloadCMD {

    public void pcCrashReloadCMD(){
        new CommandAPICommand("pcrash")
                .withArguments(new StringArgument("reload"))
                .executes((sender, args) -> {
                    if((sender instanceof Player && sender.hasPermission("pcrash.reload")) || sender instanceof ConsoleCommandSender){
                        SkyMinePCrash.getInstance().reloadConfig();
                        String message = SkyMinePCrash.getInstance().getConfig().getString("messages.reload");
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    } else if (!sender.hasPermission("pcrash.reload")){
                        String message = SkyMinePCrash.getInstance().getConfig().getString("messages.noPermission");
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                })
                .register();
    }
}
