package minigames.util;

import minigames.main.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class RoomCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Sala> §cApenas players!");
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, "Você está na sala " + RoomManager.getRoom(p));
            return false;
        }
        if (args.length == 1) {
            try {
                if (!RoomManager.isCreated(args[0])) {
                    String bb = RoomManager.getBestRoom(p, args[0]);
                    if (bb != null) {
                        RoomManager.setRoom(p, RoomManager.getRoom(p), bb);
                        return false;
                    }
                    Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Nenhuma sala encontrada com este nome.");
                    return false;
                }
                if (!RoomManager.isOpen(args[0])) {
                    Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Esta sala encontra-se fechada neste momento.");
                    return false;
                }
                if (!RoomManager.canJoin(p, args[0])) {
                    Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Você não tem permissão para entrar nesta sala.");
                    return false;
                }
                RoomManager.setRoom(p, RoomManager.getRoom(p), args[0]);
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
