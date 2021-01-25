package club.matrixstudios.framework.packets;

import club.matrixstudios.framework.packet.Packet;
import club.matrixstudios.framework.utils.JsonChain;
import com.google.gson.JsonObject;
import lombok.Getter;
import java.util.UUID;

@Getter
public class GameStartPacket implements Packet {

    private int uptime;
    private String server;

    public GameStartPacket() {}

    public GameStartPacket(int uptime, String server) {
        this.uptime = uptime;
        this.server = server;
    }


    @Override
    public int id() {
        return 0;
    }

    @Override
    public String sentFrom() {
        return server;
    }

    @Override
    public boolean selfRecieve() {
        return true;
    }


    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .addProperty("uptime", uptime)
                .addProperty("server", server)
                .get();
    }

    @Override
    public void deserialize(JsonObject jsonObject) {
        this.uptime = jsonObject.get("uptime").getAsInt();
        this.server = jsonObject.get("server").getAsString();
    }
}

