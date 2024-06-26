package ac.polarctic.plugin;

import ac.polarctic.plugin.listener.PacketEventsListener;
import ac.polarctic.plugin.listener.PlayerListener;
import ac.polarctic.plugin.manager.PlayerDataManager;
import com.github.retrooper.packetevents.PacketEvents;
import dev.thomazz.pledge.Pledge;
import dev.thomazz.pledge.pinger.frame.FrameClientPinger;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * @author Salers
 * made on ac.polarctic.plugin
 */

@Getter
public enum Global {

    INSTANCE;

    private ArcticAnticheat plugin;
    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private Pledge pledge;
    private FrameClientPinger frameClientPinger;

    public void onLoad(final ArcticAnticheat plugin) {
        this.plugin = plugin;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this.plugin));

    }

    public void onEnable() {
        PacketEvents.getAPI().init();
        this.plugin.saveDefaultConfig();
        PacketEvents.getAPI().load();
        this.registerListeners();
        this.registerPledge();

    }

    public void onDisable() {
        PacketEvents.getAPI().terminate();

    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this.plugin);
        PacketEvents.getAPI().getEventManager().registerListener(new PacketEventsListener());
    }

    private void registerPledge() {
        pledge = Pledge.getOrCreate(this.plugin);
        frameClientPinger = pledge.createFramePinger(-30000, -1);
    }
}
