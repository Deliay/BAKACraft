package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.server.ServerEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EnemyManager
{
    public List<Enemy> enemies = new LinkedList<>();

    public static EnemySpawnScheduler SPAWN_SCHEDULER;
    public static EnemyEventListener EVENT_LISTENER;

    public void joinEnemyToSpawnScheduler(Enemy enemy)
    {
        if(!enemies.contains(enemy))
        {
            enemies.add(enemy);

        }
    }

    public EnemyManager()
    {
        SPAWN_SCHEDULER = new EnemySpawnScheduler(this);
        SPAWN_SCHEDULER.runTaskTimerAsynchronously(BAKACraft.instance, 20, 20);
        EVENT_LISTENER = new EnemyEventListener(this);
        BAKACraft.instance.getServer().getPluginManager().registerEvents(EVENT_LISTENER, BAKACraft.instance);
    }


}
