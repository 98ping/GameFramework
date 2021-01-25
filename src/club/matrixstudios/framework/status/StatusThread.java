package club.matrixstudios.framework.status;

import club.matrixstudios.framework.GameFramework;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import redis.clients.jedis.Jedis;

import java.util.stream.Stream;

public class StatusThread extends Thread {

    @Getter private static boolean shutdown = false;

    public StatusThread() {
        super("HyCore - Status Thread");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {

            if(shutdown) return;

            save(ServerProperty.PROVIDERNAME, StatusHandler.getProvider().getName());
            save(ServerProperty.SERVERNAME, StatusHandler.getProvider().serverName());
            save(ServerProperty.STATUS, StatusHandler.getProvider().serverStatus());
            save(ServerProperty.ONLINE, "" +StatusHandler.getProvider().online());
            save(ServerProperty.MAXIMUM, "" + StatusHandler.getProvider().maximum());
            save(ServerProperty.MOTD, StatusHandler.getProvider().motd());
            save(ServerProperty.TPS, "" + StatusHandler.getProvider().tps());
            save(ServerProperty.LASTUPDATED, "" + System.currentTimeMillis());
            save(ServerProperty.GROUP, "" + "GAME");
            JsonObject jsonObject = StatusHandler.getProvider().dataPassthrough();
            if(jsonObject != null) {
                save(ServerProperty.DATA, jsonObject.toString());
            }
            if(StatusHandler.getProvider().players() != null) {
                save(ServerProperty.PLAYERS, new Gson().toJson(StatusHandler.getProvider().players()));
            }

            try {

                try (Jedis end = GameFramework.getPacketHandler().getJedisPool().getResource()) {
                    Stream.of(ServerProperty.values()).forEach((property) -> end.hgetAll(property.getJedisId()).forEach((server, value) -> {
                        ServerInfo.getHycoreServers().add(server);
                        StatusProvider.getServerDataTable().put(server, property, value);
                    }));
                } catch (Throwable ignored) {}
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void save(ServerProperty prop, String data) {
        if (GameFramework.getPacketHandler().getJedisPool() != null) {
            try {
                try (Jedis jedis = GameFramework.getPacketHandler().getJedisPool().getResource()) {
                    jedis.hset(prop.getJedisId(), StatusHandler.getProvider().serverName(), data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void shutdownThread() {
        shutdown = true;

        save(ServerProperty.STATUS, "OFFLINE");
        save(ServerProperty.ONLINE, "0");
        save(ServerProperty.PLAYERS, "");
    }
}
