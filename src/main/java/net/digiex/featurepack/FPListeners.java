package net.digiex.featurepack;

import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import net.digiex.featurepack.listener.FPBlockListener;
import net.digiex.featurepack.listener.FPCustomListener;
import net.digiex.featurepack.listener.FPEntityListener;
import net.digiex.featurepack.listener.FPPlayerListener;
import net.digiex.featurepack.listener.FPWorldListener;

public class FPListeners {

    private FeaturePack parent;
    public HashMap<String, Listener> listeners = new HashMap<String, Listener>();

    public FPListeners(FeaturePack parent) {
        this.parent = parent;
    }

    public static FPListeners load(FeaturePack parent) {
        FeaturePack.log.debug("Loading events and listeners");
        FPListeners listener = new FPListeners(parent);
        listener.registerListener("player", new FPPlayerListener(parent));
        listener.registerListener("entity", new FPEntityListener(parent));
        listener.registerListener("block", new FPBlockListener(parent));
        listener.registerListener("world", new FPWorldListener(parent));
        if (FPSpout.plugin != null) {
            listener.registerListener("custom", new FPCustomListener(parent));
        }
        listener.registerEvent(Event.Type.PLAYER_INTERACT, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_JOIN, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_QUIT, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_TELEPORT, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_BED_ENTER, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_MOVE, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.PLAYER_DROP_ITEM, "player",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.ENTITY_DAMAGE, "entity",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.ENTITY_DEATH, "entity",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.BLOCK_BREAK, "block",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.BLOCK_PLACE, "block",
                Event.Priority.Monitor);
        listener.registerEvent(Event.Type.WORLD_LOAD, "world",
                Event.Priority.Monitor);
        if (FPSpout.plugin != null) {
            listener.registerEvent(Event.Type.CUSTOM_EVENT, "custom",
                    Event.Priority.Monitor);
        }
        return listener;
    }

    public void registerListener(String name, Listener listener) {
        listeners.put(name, listener);
    }

    public void registerEvent(Event.Type type, String listener,
            Event.Priority priority) {
        parent.getServer().getPluginManager().registerEvent(type, listeners.get(listener), priority, parent);
    }
}