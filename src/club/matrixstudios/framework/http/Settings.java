package club.matrixstudios.framework.http;

import org.bukkit.Bukkit;

public final class Settings {

    private static String apiHost;

    private static String apiKey;

    public static String getApiHost() {
        return apiHost;
    }

    public static void setApiHost(String apiHost) {
        Settings.apiHost = "http://localhost:6969";
    }

    public static String getApiKey() {
        return Bukkit.getServer().getServerName();
    }
}
