package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SpawnCommand extends Command {

    protected SpawnCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            switch (strings[0].toLowerCase())
            {
                //e.g /enemy spawn BOSS_FINAL
                case "spawn":
                    spawnExecute(sender, strings[1]);
                    break;
                //e.g /enemy dropadd
                case "dropadd":

                    break;;
                //e.g /enemy dropremove
                case "dropremove":
            }
        }
        return true;
    }

    private void spawnExecute(Player p, String enemyName)
    {
        for (Enemy e :EnemyManager.SPAWN_SCHEDULER.spawnEnemies)
        {
            if(e.CustomName.equals(enemyName))
            {
                Location loc = p.getEyeLocation();
                LivingEntity entity = e.trySpawnToPlayer(p, loc);
                e.AsyncSpawner.registerEntity(entity);
            }
        }
    }
}
