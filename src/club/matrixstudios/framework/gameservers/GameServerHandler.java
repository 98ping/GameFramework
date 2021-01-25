package club.matrixstudios.framework.gameservers;

import club.matrixstudios.framework.http.RequestHandler;
import club.matrixstudios.framework.http.RequestResponse;
import net.frozenorb.qlib.qLib;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.util.Set;

public class GameServerHandler {

    private Set<GameServer> gameServers;

    public Set<GameServer> getGameServers() {
        return this.gameServers;


    }

    private void refresh() {
        RequestResponse response = RequestHandler.get("/gameServers");
        if (response.wasSuccessful()) {
            this.gameServers = (Set<GameServer>) qLib.PLAIN_GSON.fromJson(response.getResponse(), (new TypeToken<Set<GameServer>>() {

            }).getType());
        } else {
            Bukkit.getLogger().warning("GameServer could not resolve from API: " + response.getErrorMessage());
        }


    }
}
