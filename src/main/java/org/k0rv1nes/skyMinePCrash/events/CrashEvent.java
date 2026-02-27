package org.k0rv1nes.skyMinePCrash.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrashEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player target;
    private Player player;


    public CrashEvent(Player target, Player player) {
        this.player = player;
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
