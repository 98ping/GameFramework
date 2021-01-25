package club.matrixstudios.framework.packet.listeners;

import club.matrixstudios.framework.packet.Packet;
import club.matrixstudios.framework.packet.PacketListener;
import club.matrixstudios.framework.packets.GameStartPacket;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MainPacketListener implements PacketListener {

    @Override
    public void receive(Packet packet) {
        switch (packet.id()) {
            case 0: {
                GameStartPacket gameStartPacket = (GameStartPacket) packet;
                    Bukkit.getConsoleSender().sendMessage("§fserver uptime has exceeded " + gameStartPacket.getUptime() + " §7(" + gameStartPacket.getServer() + ")");
                }
                break;
            }


            }
        }



