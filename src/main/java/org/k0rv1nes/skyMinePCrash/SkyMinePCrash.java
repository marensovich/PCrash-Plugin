package org.k0rv1nes.skyMinePCrash;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.k0rv1nes.skyMinePCrash.commands.helpCMD;
import org.k0rv1nes.skyMinePCrash.commands.pcrashCommand;
import org.k0rv1nes.skyMinePCrash.commands.pcrashGiveCommand;
import org.k0rv1nes.skyMinePCrash.commands.reloadCMD;
import org.k0rv1nes.skyMinePCrash.listeners.CrashEventListener;

public final class SkyMinePCrash extends JavaPlugin {
    private static ProtocolManager manager;

    public static SkyMinePCrash getInstance() {
        return instance;
    }

    private static SkyMinePCrash instance;


    @Override
    public void onLoad(){
        saveDefaultConfig();
        chechDependencies();
        validateConfig();
    }

    @Override
    public void onEnable() {
        instance = this;



        manager = ProtocolLibrary.getProtocolManager();

        helpCMD help_cmd = new helpCMD();
        pcrashCommand pcrash_cmd = new pcrashCommand();
        pcrashGiveCommand pcrash_give_cmd = new pcrashGiveCommand();
        reloadCMD reload_cmd = new reloadCMD();

        pcrash_cmd.pcrashCMD();
        help_cmd.HelpCMD();
        pcrash_give_cmd.pcrashGiveCMD();
        reload_cmd.pcCrashReloadCMD();



        getServer().getPluginManager().registerEvents(new CrashEventListener(), this);
        getLogger().info("PCrash enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PCrash disabled!");
    }


    public static ProtocolManager getManager() {
        return manager;
    }

    private void validateConfig() {
        if (getConfig().getBoolean("use_webhook")){
            String webhookURL = getConfig().getString("webhookURL", "");
            String message = getConfig().getString("messages.noWebHook");
            if (webhookURL == null || webhookURL.trim().isEmpty()) {
                getLogger().severe(message);
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        return;
    }

    private void chechDependencies(){
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().severe("ProtocolLib не найден! Плагин не может работать без него.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

}
