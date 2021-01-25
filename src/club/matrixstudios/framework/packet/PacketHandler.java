package club.matrixstudios.framework.packet;

import club.matrixstudios.framework.GameFramework;
import club.matrixstudios.framework.subscriber.RedisSubscriber;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PacketHandler {

    @Getter private JedisPool jedisPool;
    @Getter private Map<Integer, Class> packets = new HashMap<>();
    @Getter private Set<PacketListener> listeners = new HashSet<>();
    @Getter private String channel;

    public PacketHandler(String channel) {
        this.channel = channel;
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 0, (null), 0);
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.subscribe(new RedisSubscriber(), channel);
        }).start();
    }

    public void sendPacket(Packet packet) {
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.publish(channel, packet.id() + "¸" + packet.serialize());
            jedis.close();
        }).start();
        Bukkit.getConsoleSender().sendMessage("Sent Packet: " + packet.id() + " - " + packet.getClass().getSimpleName() );

    }

    public void handlePacket(Packet packet) {
        boolean cancel = !packet.selfRecieve();
        Bukkit.getConsoleSender().sendMessage("Incoming Packet §7(" + packet.id() + ") §f - Detected Class: " + getPacketByID(packet.id()).getClass().getSimpleName() + (cancel ? " §c[CANCELLED]" : ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Packet intercepted on main interface");
        if(!cancel) this.listeners.forEach(packetListener -> packetListener.receive(packet));
    }

    public Packet getPacketByID(int id) {
        if (!packets.containsKey(id)) {
            return null;
        }
        try {
            return (Packet) packets.get(id).newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerPacket(Class packet) {
        try {
            int id = (int)packet.getDeclaredMethod("id", (Class<?>[])new Class[0]).invoke(packet.newInstance(), null);
            if (packets.containsKey(id)) {
                throw new IllegalStateException("Packet with that ID already exists");
            }
            packets.put(id, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerPackets(Class... packets) {
        for (final Class packet : packets) {
            this.registerPacket(packet);
        }
    }

    public void registerListener(PacketListener packetListener) {
        try {
            if (listeners.contains(packetListener)) {
                throw new IllegalStateException("Packet listener already registered");
            }
            listeners.add(packetListener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
