package club.matrixstudios.framework.Commands;

import club.matrixstudios.framework.availability.GameEnum;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class StartCommand {

    @Command(names = {"game start"}, permission = "framework.start")
    public static void serverInfo(CommandSender sender, @Param(name = "server")String server) throws IOException {
        Desktop.getDesktop().open(new File("X:\\Servers\\Overtake-Lobby\\" + server + ".bat"));
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "ServerMonitor" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Server " + server + " created");




            }
        }

