package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class EnemySpawnScheduler extends BukkitRunnable {

    EnemyManager manager;
    List<Enemy> spawnEnemies = new LinkedList<>();
    HashMap<Enemy, AsyncEnemySpawn> spawnMap = new LinkedHashMap<>();

    public EnemySpawnScheduler(final EnemyManager parent)
    {
        manager = parent;
    }

    public final List<Enemy> getSpawnEnemies() {
        return spawnEnemies;
    }

    public HashMap<Enemy, AsyncEnemySpawn> getSpawnMap() {
        return spawnMap;
    }

    public final boolean Exist(final Enemy enemy)
    {
        return spawnEnemies.contains(enemy);
    }

    public final LivingEntity getLivingEntity(final UUID uid)
    {
        for (AsyncEnemySpawn item : spawnMap.values())
        {
            return item.getLivingEntity(uid);
        }
        return null;
    }

    public void registerEnemyToSpawnScheduler(final Enemy enemy)
    {
        spawnEnemies.add(enemy);
        AsyncEnemySpawn spawn = new AsyncEnemySpawn(enemy);
        spawnMap.put(enemy, spawn);
        enemy.AsyncSpawner = spawn;
    }

    @Override
    public void run() {
        for (AsyncEnemySpawn e : spawnMap.values())
        {
            e.run();
        }
    }
}
