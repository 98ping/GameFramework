package club.matrixstudios.framework.exceptions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ThrowException {

    private static void GameFramework(String[] args) {
        Path path = Paths.get("X:\\Servers\\Bunkers-Lobby\\bunkers01.bat");

        if(Files.exists(path)) {
            if (Files.isRegularFile(path)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Framework detected that bunkers01. exists and is starting");

            }
        }
    }
}
