package minigames.util;

import minigames.main.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AdmCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4ADM> §cApenas players!");
            return false;
        }
        Player p = (Player) sender;
        if (!RankManager.containsRole(p.getName(), "Administrador") || !p.isOp()) {
            p.sendMessage("§4Permissão> §cVocê não pode usar este comando!");
            return false;
        }
        if (args.length == 0) {
            p.sendMessage("§6ADM> §bPossíveis argumentos");
            p.sendMessage("§6ADM> §b=-=-=-=Edição de Salas=-=-=-=");
            p.sendMessage("§6ADM> §b-createroom <nome> <mundo> <cargo> <aberta>: Cria uma nova sala;");
            p.sendMessage("§6ADM> §b-closeroom <nome>: Fecha a sala;");
            p.sendMessage("§6ADM> §b-openroom <nome>: Abre a sala;");
            p.sendMessage("§6ADM> §b-restartroom <nome>: Reinicia uma sala;");
            p.sendMessage("§6ADM> §b-sendroom <player> <nome>: Envia um player a uma sala;");
            p.sendMessage("§6ADM> §b-roomset <nome/world/role> <novo valor>: Edita um parâmetro da sala;");
            p.sendMessage("§6ADM> §b-deleteroom <nome>: Deleta uma sala.");
            p.sendMessage("§6ADM> §b=-=-=-=Edição de Cargos=-=-=-=");
            p.sendMessage("§6ADM> §b-setrole <nome> <cargo>: Remove todos os cargos e seta para este.");
            p.sendMessage("§6ADM> §b-giverole <nome> <cargo>: Adiciona um cargo a um jogador");
            p.sendMessage("§6ADM> §b-remrole <nome> <cargo>: Remove um cargo de um jogador");
            return false;
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("createroom")) {
                if (args.length != 5) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-createroom <nome> <mundo> <cargo> <aberta>: Cria uma nova sala;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome para nova sala;");
                    p.sendMessage("§6ADM> §b--<mundo>: Nome do mundo da sala;");
                    p.sendMessage("§6ADM> §b--<cargo>: Nome do cargo necessário, para nenhum use 'Normal';");
                    p.sendMessage("§6ADM> §b--<aberta>: Define o estado da sala, 1 = aberto e 0 = fechado.");
                    return false;
                }
                boolean op = false;
                if (args[4].equalsIgnoreCase("1")) {
                    op = true;
                }
                RoomManager.createRoom(p, args[1], args[2], args[3], op);
                return false;
            }
            if (args[0].equalsIgnoreCase("closeroom")) {
                if (args.length != 2) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-closeroom <nome>: Fecha a sala;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome da sala;");
                    return false;
                }
                try {
                    RoomManager.closeRoom(p, args[1]);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("openroom")) {
                if (args.length != 2) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-openroom <nome>: Abre a sala;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome da sala;");
                    return false;
                }
                try {
                    RoomManager.openRoom(p, args[1]);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("sendroom")) {
                if (args.length != 3) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-sendroom <player> <nome>: Envia um player a uma sala;");
                    p.sendMessage("§6ADM> §b--<player>: Nome do jogador;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome da sala;");
                    return false;
                }
                if (!(Bukkit.getPlayer(args[1]) instanceof Player)) {
                    p.sendMessage("§4ADM> §cJogador não está online!");
                    return false;
                }
                Player a = Bukkit.getPlayer(args[1]);
                try {
                    RoomManager.setRoom(a, RoomManager.getRoom(a), args[2]);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("deleteroom")) {
                if (args.length != 2) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-deleteroom <nome>: Deleta uma sala.;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome da sala;");
                    return false;
                }
                try {
                    RoomManager.deleteRoom(p, args[1]);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("restartroom")) {
                if (args.length != 2) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-restartroom <nome>: Reinicia uma sala.;");
                    p.sendMessage("§6ADM> §b--<nome>: Nome da sala;");
                    return false;
                }
                RoomManager.restartRoom(p, args[1]);
                return false;
            }
            if (args[0].equalsIgnoreCase("setrole")) {
                if (args.length != 3) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-setrole <nome> <cargo>: Remove todos os cargos e seta para este.");
                    p.sendMessage("§6ADM> §b--<nome>: Nome do jogador;");
                    p.sendMessage("§6ADM> §b--<cargo>: Nome do cargo;");
                    return false;
                }
                try {
                    if (RankManager.setRole(args[1], args[2])) {
                        Messages.sendMessage(p, Messages.MessageType.ROLE, "Difinido cargo " + args[2] + " de " + args[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }
            if (args[0].equalsIgnoreCase("giverole")) {
                if (args.length != 3) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-giverole <nome> <cargo>: Adiciona um cargo a um jogador");
                    p.sendMessage("§6ADM> §b--<nome>: Nome do jogador;");
                    p.sendMessage("§6ADM> §b--<cargo>: Nome do cargo;");
                    return false;
                }
                try {
                    if (RankManager.giveRole(args[1], args[2])) {
                        Messages.sendMessage(p, Messages.MessageType.ROLE, "Dado cargo " + args[2] + " para " + args[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }
            if (args[0].equalsIgnoreCase("remrole")) {
                if (args.length != 3) {
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-remrole <nome> <cargo>: Remove um cargo de um jogador");
                    p.sendMessage("§6ADM> §b--<nome>: Nome do jogador;");
                    p.sendMessage("§6ADM> §b--<cargo>: Nome do cargo;");
                    return false;
                }
                try {
                    if (RankManager.removeRole(args[1], args[2])) {
                        Messages.sendMessage(p, Messages.MessageType.ROLE, "Removido cargo " + args[2] + " de " + args[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }

        }
        return false;
    }

}
