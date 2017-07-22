package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.EnemySpawner;
import bakacraft.ICommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SpawnCommand implements ICommand {

    public static final String NAME = "enemy";

    public SpawnCommand()
    {
        BAKACraft.registerCommandDispatcher(this);
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if(!sender.isOp()) return false;
            if(strings.length == 0) return false;
            switch (strings[0].toLowerCase())
            {
                //e.g /enemy spawn BOSS_FINAL
                case "spawn":
                    spawnExecute(sender, strings[1]);
                    break;
                //e.g /enemy dropadd BOSS_FINAL DropFinalSword 45
                case "dropadd":
                    addDrop(strings[1], strings[2], sender, strings[3]);
                    break;
                //e.g /enemy dropremove BOSS_FINAL
                case "dropremove":
                    removeDrop(strings[1]);
                    break;
                case "removelastdrop":
                    // /enemy dropremove BOSS_FINAL
                    removeLastDrop(strings[1]);
                    break;
                case "saveall":
                    EnemyStorage.getInstance().saveAll();
                    break;
                case "reload":
                    EnemySpawner.ENEMY_MANAGER = null;
                    EnemySpawner.ENEMY_MANAGER = new EnemyManager();
            }
        }
        return true;
    }

    private void removeLastDrop(String enemy)
    {
        for (Enemy e :EnemyManager.SPAWN_SCHEDULER.spawnEnemies)
        {
            if(e.Name.equals(enemy))
            {
                e.Metadata.drops.removeLast();
                return;
            }
        }
    }

    private void addDrop(String enemy, String drop, Player p, String chance)
    {
        for (Enemy e :EnemyManager.SPAWN_SCHEDULER.spawnEnemies)
        {
            if(e.Name.equals(enemy))
            {
                e.Metadata.drops.addItem(drop, p.getItemInHand(), Integer.parseInt(chance));
                return;
            }
        }
    }

    private void removeDrop(String enemy)
    {
        for (Enemy e :EnemyManager.SPAWN_SCHEDULER.spawnEnemies)
        {
            if(e.Name.equals(enemy))
            {
                e.Metadata.drops.removeAllItem();
                return;
            }
        }
    }

    private void spawnExecute(Player p, String enemyName)
    {
        for (Enemy e :EnemyManager.SPAWN_SCHEDULER.spawnEnemies)
        {
            if(e.Name.equals(enemyName))
            {
                Location loc = p.getEyeLocation();
                LivingEntity entity = e.trySpawnToPlayer(p, loc);
                e.AsyncSpawner.registerEntity(entity);
                return;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String s, String[] strings) {
        return execute(commandSender, s, strings);
    }

    @Override
    public String getName() {
        return SpawnCommand.NAME;
    }
}
