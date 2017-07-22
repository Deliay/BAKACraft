package bakacraft;

import org.bukkit.command.CommandSender;

public interface ICommand {
    String getName();
    boolean onCommand(CommandSender commandSender, String s, String[] strings);
}
