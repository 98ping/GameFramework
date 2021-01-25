package club.matrixstudios.framework.subscriber;

import club.matrixstudios.framework.GameFramework;
import club.matrixstudios.framework.packet.Packet;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {

        new Thread(() -> {
            if (channel.equals(GameFramework.getPacketHandler().getChannel())) {
                String[] args = message.split("¸");
                int id;
                try {
                    id = Integer.parseInt(args[0]);
                } catch (Exception ignored) {
                    return;
                }

                Packet packet = GameFramework.getPacketHandler().getPacketByID(id);
                if (packet == null) {
                    Bukkit.getConsoleSender().sendMessage("[Packet Handler] Failed to find a packet with the ID: " + id);
                    return;
                }
                packet.deserialize(new JsonParser().parse(args[1]).getAsJsonObject());
                GameFramework.getPacketHandler().handlePacket(packet);
            }
        }).start();
    }

}
