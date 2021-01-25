package club.matrixstudios.framework.Commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateCommand {

    @Command(names = {"game create"}, permission = "framework.data", hidden = true)
    public static void serverInfo(CommandSender s, @Param(name = "server")String server) throws IOException {
        new File("X:/Servers/" + server).mkdir();
        s.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "ServerMonitor" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Server instance " + server + " created!");

        }
    }




