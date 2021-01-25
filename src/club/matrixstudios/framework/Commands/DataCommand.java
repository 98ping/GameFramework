package club.matrixstudios.framework.Commands;

import club.matrixstudios.framework.status.ServerInfo;
import club.matrixstudios.framework.status.ServerProperty;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DataCommand {

    @Command(names = {"game data"}, permission = "framework.data", hidden = true)
    public static void serverInfo(CommandSender s, @Param(name = "server")String server) {
        if(!ServerInfo.serverExists(server)) {
            s.sendMessage(ChatColor.RED + "There is no such server with the name \"" + server + "\".");
            return;
        }
        String serv = ServerInfo.getProperName(server);
        s.sendMessage(StringUtils.repeat(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-", 45));
        s.sendMessage(ChatColor.BLUE + serv + ChatColor.GRAY + " [" + ServerInfo.getProperty(serv, ServerProperty.ONLINE) + '/' + ServerInfo.getProperty(serv, ServerProperty.MAXIMUM) + "]");
        s.sendMessage(ChatColor.YELLOW + "Using Provider: " + ChatColor.RED + ServerInfo.getProperty(serv, ServerProperty.PROVIDERNAME));
        s.sendMessage(ChatColor.YELLOW + "Current Status: " + ServerInfo.formattedStatus(serv, true));
        s.sendMessage(ChatColor.YELLOW + "MOTD: " + ChatColor.RED + ServerInfo.getProperty(serv, ServerProperty.MOTD));
        s.  sendMessage(StringUtils.repeat(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-", 45));
    }
}
