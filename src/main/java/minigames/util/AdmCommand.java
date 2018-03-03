package minigames.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdmCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4ADM> §cApenas players!");
            return false;
        }
        Player p = (Player) sender;
        if (!RankManager.containsRole(p, "Administrador")) {
            p.sendMessage("§4Permissão> §cVocê não pode usar este comando!");
            return false;
        }
        if (args.length == 0) {
            p.sendMessage("§6ADM> §bPossíveis argumentos");
            p.sendMessage("§6ADM> §b-createroom <nome> <mundo> <cargo> <aberta>: Cria uma nova sala;");
            p.sendMessage("§6ADM> §b-closeroom <nome>: Fecha a sala;");
            p.sendMessage("§6ADM> §b-openroom <nome>: Abre a sala;");
            p.sendMessage("§6ADM> §b-restartroom <nome>: Reinicia uma sala;");
            p.sendMessage("§6ADM> §b-sendroom <player> <nome>: Envia um player a uma sala;");
            p.sendMessage("§6ADM> §b-roomset <nome/world/role> <novo valor>: Edita um parâmetro da sala;");
            p.sendMessage("§6ADM> §b-deleteroom <nome>: Deleta uma sala.");
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
        }
        return false;
    }

}
