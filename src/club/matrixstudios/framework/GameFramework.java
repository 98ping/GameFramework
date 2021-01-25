package club.matrixstudios.framework;

import club.matrixstudios.framework.gameservers.GameServerHandler;
import club.matrixstudios.framework.packet.PacketHandler;
import club.matrixstudios.framework.packet.listeners.MainPacketListener;
import club.matrixstudios.framework.packets.GameStartPacket;
import club.matrixstudios.framework.status.StatusHandler;
import club.matrixstudios.framework.status.StatusThread;
import lombok.Getter;
import net.frozenorb.qlib.command.FrozenCommandHandler;
import okhttp3.OkHttpClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class GameFramework extends JavaPlugin {

    @Getter private static String redisChannel;

    @Getter
    private static PacketHandler packetHandler;

    public static StatusThread statusThread;


    @Getter private static boolean  redisAuth;

    @Getter private static String redisPassword;

    private GameServerHandler serverHandler;

    public GameServerHandler getServerHandler() {
        return this.serverHandler;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static final OkHttpClient okHttpClient = (new OkHttpClient.Builder())
            .connectTimeout(2L, TimeUnit.SECONDS)
            .writeTimeout(2L, TimeUnit.SECONDS)
            .readTimeout(2L, TimeUnit.SECONDS)
            .build();


    @Override
    public void onEnable() {



        FrozenCommandHandler.registerAll(this);
        Bukkit.getConsoleSender().sendMessage("Setting up Packet System");
        packetHandler = new PacketHandler(redisChannel);

        Bukkit.getConsoleSender().sendMessage("Registering all packets");
        getPacketHandler().registerPackets(GameStartPacket.class);

        Bukkit.getConsoleSender().sendMessage("Registering Packet Listener");
        getPacketHandler().registerListener(new MainPacketListener());

        Bukkit.getConsoleSender().sendMessage("Setting up status handler");
        StatusHandler.init();








    }
}
