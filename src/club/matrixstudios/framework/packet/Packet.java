package club.matrixstudios.framework.packet;

import com.google.gson.JsonObject;

public interface Packet {

    int id();

    String sentFrom();

    boolean selfRecieve();

    JsonObject serialize();

    void deserialize(JsonObject jsonObject);

}

