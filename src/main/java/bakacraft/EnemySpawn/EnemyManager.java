package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakalibs.CConfiger;

import java.util.LinkedList;
import java.util.List;

public class EnemyManager
{
    public List<Enemy> enemiesCollection = new LinkedList<>();

    public static EnemySpawnScheduler SPAWN_SCHEDULER;

    public void joinEnemyToSpawnScheduler(Enemy enemy)
    {
        if(!SPAWN_SCHEDULER.Exist(enemy))
        {
            SPAWN_SCHEDULER.registerEnemyToSpawnScheduler(enemy);
        }
    }

    public Enemy findEnemy(String name)
    {
        for (Enemy e : enemiesCollection)
        {
            if(e.Name.equals(name)) return e;
        }
        return null;
    }

    public EnemyManager()
    {
        SPAWN_SCHEDULER = new EnemySpawnScheduler(this);
        SPAWN_SCHEDULER.runTaskTimerAsynchronously(BAKACraft.instance, 20, 20);
        CConfiger config = new CConfiger(BAKACraft.instance, "EnemyList.yml");
        for (String key : config.getKeys(false))
        {
            Enemy enemy = new Enemy(key, config.getConfigurationSection(key));
            joinEnemyToSpawnScheduler(enemy);
            enemiesCollection.add(enemy);
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
