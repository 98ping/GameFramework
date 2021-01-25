package club.matrixstudios.framework.status;

public enum ServerProperty {

    PROVIDERNAME, SERVERNAME, ONLINE, MAXIMUM, STATUS, MOTD, TPS, LASTUPDATED, DATA, GROUP, PLAYERS;

    public String getJedisId() {
        return "GameFramework" + ":" + this.name().toLowerCase();
    }
}
