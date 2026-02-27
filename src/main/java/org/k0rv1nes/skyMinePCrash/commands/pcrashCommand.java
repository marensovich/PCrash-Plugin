package org.k0rv1nes.skyMinePCrash.commands;


import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.SafeSuggestions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;
import org.k0rv1nes.skyMinePCrash.discord.SendDiscord;
import org.k0rv1nes.skyMinePCrash.events.CrashEvent;


public class pcrashCommand {
    SendDiscord log = new SendDiscord();

    public void pcrashCMD() {

        new CommandAPICommand("pcrash")
                .withSubcommand(new CommandAPICommand("crash"))
                    .withArguments(
                            new PlayerArgument("player").
                                    replaceSafeSuggestions(
                                            SafeSuggestions.suggest(info ->
                                                    Bukkit.getOnlinePlayers().toArray(new Player[0]))))
                    .executes((sender, args) -> {
                        Player player = (Player) sender;
                        Player target = (Player) args.get("player");

                        if (target == null || !target.isOnline()) {
                            String message = SkyMinePCrash.getInstance().getConfig().getString("messages.player-offline");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            return;
                        }
                        String method = "command";
                        SkyMinePCrash.getInstance().getServer().getPluginManager().callEvent(new CrashEvent(player, target));
                        if (SkyMinePCrash.getInstance().getConfig().getBoolean("use_webhook")){
                            log.sendWebHookKick(player, target, method);
                        }


                    })
                    .register();
    }


}
