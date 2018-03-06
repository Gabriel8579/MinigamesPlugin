package minigames.util;

import minigames.main.Messages;
import minigames.tntwars.TntWars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;

public class AdmGameCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Você precisa ser um player!");
            return false;
        }
        Player p = (Player) sender;
        if (RankManager.containsRole(p.getName(), "administrador") || p.isOp()) {
            if (args.length == 0) {
                p.sendMessage("§6ADM> §bPossíveis argumentos");
                p.sendMessage("§6ADM> §b-create <sala> <minigame>: Cria uma arena de minigame");
                p.sendMessage("§6ADM> §b-set <sala> <minigame> <parametro>: Seta uma variavel do minigame");
                p.sendMessage("§6ADM> §b-open <sala>: Abre a sala do minigame");
                p.sendMessage("§6ADM> §b-start <sala>: Inicia uma partida");
                p.sendMessage("§6ADM> §b-stop <sala>: Para uma partida");
                return false;
            }
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length != 3) {
                        p.sendMessage("§6ADM> §bPossíveis argumentos");
                        p.sendMessage("§6ADM> §b-create <sala> <minigame>: Cria uma arena de minigame");
                        p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                        p.sendMessage("§6ADM> §b--<minigame>: Nome do minigame");
                        return false;
                    }
                    if (args[2].equalsIgnoreCase("tntwars")) {
                        try {
                            TntWars.createTntWars(args[1], p);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InvalidConfigurationException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                    p.sendMessage("§6ADM> §bPossíveis argumentos");
                    p.sendMessage("§6ADM> §b-create <sala> <minigame>: Cria uma arena de minigame");
                    p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                    p.sendMessage("§6ADM> §b--<minigame>: Nome do minigame");
                    return false;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length != 4) {
                        p.sendMessage("§6ADM> §bPossíveis argumentos");
                        p.sendMessage("§6ADM> §b-set <sala> <minigame> <parametro>: Seta uma variavel do minigame");
                        p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                        p.sendMessage("§6ADM> §b--<minigame>: Nome do minigame");
                        p.sendMessage("§6ADM> §b--<parametro>: Nome do parametro");
                        p.sendMessage("§6ADM> §b-Parâmetros para TntWars:");
                        p.sendMessage("§6ADM> §b--spawnred: Seta o spawn do time vermelho");
                        p.sendMessage("§6ADM> §b--spawnblue: Seta o spawn do time azul");
                        p.sendMessage("§6ADM> §b--spawnlobby: Seta o spawn do lobby de espera");
                        return false;
                    }
                    if (args[2].equalsIgnoreCase("tntwars")) {
                        if (args[3].equalsIgnoreCase("spawnred")) {
                            try {
                                TntWars.setRedSpawn(p, args[1]);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                        if (args[3].equalsIgnoreCase("spawnblue")) {
                            try {
                                TntWars.setBlueSpawn(p, args[1]);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                        if (args[3].equalsIgnoreCase("spawnlobby")) {
                            try {
                                TntWars.setLobbySpawn(p, args[1]);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                        p.sendMessage("§6ADM> §bPossíveis argumentos");
                        p.sendMessage("§6ADM> §b-set <sala> <minigame> <parametro>: Seta uma variavel do minigame");
                        p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                        p.sendMessage("§6ADM> §b--<minigame>: Nome do minigame");
                        p.sendMessage("§6ADM> §b--<parametro>: Nome do parametro");
                        p.sendMessage("§6ADM> §b-Parâmetros para TntWars:");
                        p.sendMessage("§6ADM> §b--spawnred: Seta o spawn do time vermelho");
                        p.sendMessage("§6ADM> §b--spawnblue: Seta o spawn do time azul");
                        p.sendMessage("§6ADM> §b--spawnlobby: Seta o spawn do lobby de espera");
                        return false;
                    }
                }
                if (args[0].equalsIgnoreCase("open")) {
                    if (args.length != 2) {
                        p.sendMessage("§6ADM> §bPossíveis argumentos");
                        p.sendMessage("§6ADM> §b-open <sala>: Abre a sala do minigame");
                        p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                        return false;
                    }
                    try {
                        TntWars.open(p, args[1]);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("start")) {
                    if (args.length != 2) {
                        p.sendMessage("§6ADM> §bPossíveis argumentos");
                        p.sendMessage("§6ADM> §b-start <sala>: Inicia uma partida");
                        p.sendMessage("§6ADM> §b--<sala>: Nome da sala (case sensitivy)");
                        return false;
                    }
                    TntWars.startCount(args[1]);
                    return false;
                }
            }
        } else {
            Messages.sendMessage(p, Messages.MessageType.ROLE, "§cVocê não pode usar este comando!");
            return false;
        }
        return false;
    }
}
