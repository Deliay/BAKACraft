package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakalibs.CConfiger;

import java.util.LinkedList;
import java.util.List;

public class EnemyManager
{
    public List<Enemy> enemiesCollection = new LinkedList<>();

    public static EnemySpawnScheduler SPAWN_SCHEDULER;
    public static EnemyEventListener EVENT_LISTENER;

    public void joinEnemyToSpawnScheduler(Enemy enemy)
    {
        if(!SPAWN_SCHEDULER.Exist(enemy))
        {
            SPAWN_SCHEDULER.registerEnemyToSpawnScheduler(enemy);
        }
    }

    public EnemyManager()
    {
        SPAWN_SCHEDULER = new EnemySpawnScheduler(this);
        SPAWN_SCHEDULER.runTaskTimerAsynchronously(BAKACraft.instance, 20, 20);
        EVENT_LISTENER = new EnemyEventListener(this);
        BAKACraft.instance.getServer().getPluginManager().registerEvents(EVENT_LISTENER, BAKACraft.instance);
        CConfiger config = new CConfiger(BAKACraft.instance, "EnemyList.yml");
        for (String key : config.getKeys(false))
        {
            Enemy enemy = new Enemy(key, config.getConfigurationSection(key));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        for (AsyncEnemySpawn e : SPAWN_SCHEDULER.getSpawnMap().values())
        {
            e.cancel();
        }
        SPAWN_SCHEDULER.cancel();
        super.finalize();
    }
}
