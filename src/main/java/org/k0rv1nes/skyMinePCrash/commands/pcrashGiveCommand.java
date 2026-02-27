package org.k0rv1nes.skyMinePCrash.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.k0rv1nes.skyMinePCrash.Items.CrashItem;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;
import org.k0rv1nes.skyMinePCrash.discord.SendDiscord;


public class pcrashGiveCommand {

    CrashItem item = new CrashItem();
    SendDiscord log = new SendDiscord();

    public void pcrashGiveCMD(){
        new CommandAPICommand("pcrash")
                .withSubcommand(new CommandAPICommand("give"))
                    .withArguments(new PlayerArgument("player"))
                        .executes((sender, args) -> {
                            if (sender instanceof ConsoleCommandSender){
                                String message = SkyMinePCrash.getInstance().getConfig().getString("messages.only-player-command");
                                SkyMinePCrash.getInstance().getLogger().severe(message);
                            }
                            Player player = (Player) args.get("player");
                            String uniqueID = item.giveCrashStick(player);
                            if (SkyMinePCrash.getInstance().getConfig().getBoolean("use_webhook")){
                                log.sendWebHookGiveStick(player, uniqueID);
                            }
                        })
                        .register();
    }

}
